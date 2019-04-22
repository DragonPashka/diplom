package ru.crypthocurrency.model;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class Restrictions
{
/*    private static String coinBase="https://api.coinbase.com/v2/prices/%s-USD/%s";
    private static String etherScanToken="https://etherscan.io/token/%s";
    private static String etherScanTransactions="http://api.etherscan.io/api?module=account&action=txlist&address=%s&startblock=earnest&endblock=latest&sort=asc&apikey=YourApiKeyToken";

      public static void setHoldersAndTransfers(CrypthoCurrencyEntity currencyEntity) throws IOException
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
    }

    public static void setOfficialSite(CrypthoCurrencyEntity currencyEntity) throws IOException {
        Document document = Jsoup.connect(String.format(etherScanToken, currencyEntity.getAddress())).get();
        Element element = document.getElementById("ContentPlaceHolder1_tr_officialsite_1");
        if (element!=null) {
            Elements tds = element.getElementsByTag("a");
            for (Element td: tds ) {
                if (!td.attr("href").isEmpty())
                currencyEntity.setSite(td.attr("href"));
            }
        }
    }*/

}
