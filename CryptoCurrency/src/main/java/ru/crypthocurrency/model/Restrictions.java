package ru.crypthocurrency.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import jdk.nashorn.internal.ir.ObjectNode;
import net.minidev.json.JSONValue;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.crypthocurrency.repositories.CrypthoCurrencyRepository;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Restrictions
{
    private static String coinBase="https://api.coinbase.com/v2/prices/%s-USD/%s";
    private static String etherScanToken="https://etherscan.io/token/%s";
    private static String etherScanTransactions="http://api.etherscan.io/api?module=account&action=txlist&address=%s&startblock=earnest&endblock=latest&sort=asc&apikey=YourApiKeyToken";

    //Если валюта зарестрирована то вернет её экзямпляр заполненый с ценой продажи
    // и покупки в долларах, на сегоднявшее число иначе вернет null
    public static CrypthoCurrencyEntity isRegisteredCoinBase(CrypthoCurrencyEntity currencyEntity)
    {
        try
        {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseBuy = restTemplate.getForEntity(String.format(coinBase, currencyEntity.getName(),"buy"), String.class);
            ResponseEntity<String> responseSell = restTemplate.getForEntity(String.format(coinBase, currencyEntity.getName(),"sell"), String.class);

            JSONObject responseJSON= null;

            responseJSON = new JSONObject(responseBuy.getBody());
            JSONObject data = (JSONObject) responseJSON.get("data");
            currencyEntity.setBuyPrice(new BigDecimal(data.getString("amount")));

            responseJSON=new JSONObject(responseSell.getBody());
            data = (JSONObject) responseJSON.get("data");
            currencyEntity.setSellPrice(new BigDecimal(data.getString("amount")));

        } catch (JSONException | HttpClientErrorException e) {
            return currencyEntity;
        }


        return  currencyEntity;
    }

    public static CrypthoCurrencyEntity setHoldersAndTransfers(CrypthoCurrencyEntity currencyEntity) throws IOException
    {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        HtmlPage page = webClient.getPage(String.format(etherScanToken, currencyEntity.getAddress()));

        System.out.println(String.format(etherScanToken, currencyEntity.getAddress()));

        List<HtmlElement> tds = page.getElementById("ContentPlaceHolder1_tr_tokenHolders").getElementsByTagName("td");

        for (HtmlElement td : tds) {
            if (!td.getTextContent().equals("Holders:") && td.getTextContent().indexOf('a')!=-1) {
                currencyEntity.setHolders(new BigDecimal(td.getTextContent().substring(0, td.getTextContent().indexOf(' ')).trim()));
            }
        }
        currencyEntity.setTransfers(new BigDecimal(page.getElementById("totaltxns").getTextContent().trim()));
        return currencyEntity;
    }

    public static CrypthoCurrencyEntity setOfficialSite(CrypthoCurrencyEntity currencyEntity) throws IOException {
        Document document = Jsoup.connect(String.format(etherScanToken, currencyEntity.getAddress())).get();
        Element element = document.getElementById("ContentPlaceHolder1_tr_officialsite_1");
        if (element!=null) {
            Elements tds = element.getElementsByTag("a");
            for (Element td: tds ) {
                if (!td.attr("href").isEmpty())
                currencyEntity.setSite(td.attr("href"));
            }
        }
        return currencyEntity;
    }

    public static CrypthoCurrencyEntity AverageTenTrasactionInMonth(CrypthoCurrencyEntity currencyEntity) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
        headers.set("content-type", "application/json");
        HttpEntity entity = new HttpEntity(headers);



        ResponseEntity<String> responseBuy = restTemplate.exchange(String.format(etherScanTransactions, currencyEntity.getAddress()),HttpMethod.GET, entity, String.class);
        JSONObject responseJSON= null;
        int [] amount=new int[]{0, 0, 0, 0, 0, 0};
        responseJSON = new JSONObject(responseBuy.getBody());
        JSONArray jsonArray= (JSONArray) responseJSON.get("result");
        for (int i=0; i<jsonArray.length(); i++)
        {
            long timeT=jsonArray.getJSONObject(i).getLong("timeStamp");
            for (int j=0; j<amount.length; j++)
            {
                if(amount[j]<10 && MoreThenMounthNumber(timeT, (-1)*j))
                {
                    amount[j]++;
                    break;
                }
            }
        }
        if (averageTen(amount))
            currencyEntity.setAverage("true");

        return currencyEntity;
    }

    private static boolean averageTen(int [] amount){
        for (int j=0; j<amount.length; j++)
        {
            if (amount[j]<10)
                return false;
        }
        return true;
    }

    private static boolean MoreThenMounthNumber(long time, int number){
        Date date=new Date();
        Date tDate=new Date(time*1000l);

        Calendar cal = Calendar.getInstance();
        Calendar calT = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, number);
        calT.setTime(tDate);

        return (cal.get(Calendar.MONTH)== calT.get(Calendar.MONTH));
    }


    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        for (Iterator<String> iter = list.listIterator(); iter.hasNext(); ) {
            String a = iter.next();
            if (a=="b" || a=="d") {
                iter.remove();
            }
        }
        for(String s: list)
            System.out.println(s);

    }




    /*public static void main(String[] args) throws IOException, JSONException {
        CrypthoCurrencyEntity crypthoCurrencyEntity = new CrypthoCurrencyEntity();
        crypthoCurrencyEntity.setName("BTC");
        isRegisteredCoinBase(crypthoCurrencyEntity);
    }
*/

}
