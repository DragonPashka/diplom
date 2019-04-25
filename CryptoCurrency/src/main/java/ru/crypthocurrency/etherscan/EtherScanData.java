package ru.crypthocurrency.etherscan;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class EtherScanData {
    private String etherScanToken = "https://etherscan.io/token/%s";

    public void setHoldersAndTransfers(CrypthoCurrencyEntity currencyEntity) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        HtmlPage page = webClient.getPage(String.format(etherScanToken, currencyEntity.getAddress()));
        webClient.waitForBackgroundJavaScript(10000);
        List<HtmlElement> divs = page.getElementById("ContentPlaceHolder1_tr_tokenHolders").getElementsByTagName("div");

        HtmlElement div = divs.get(2);

        currencyEntity.setHolders(convertStringToBigDecimal(div.getTextContent(), true));

        currencyEntity.setTransfers(convertStringToBigDecimal(page.getElementById("totaltxns").getTextContent(), false));
    }

    public void setOfficialSite(CrypthoCurrencyEntity currencyEntity) throws IOException {
        Document document = Jsoup.connect(String.format(etherScanToken, currencyEntity.getAddress())).get();
        Element element = document.getElementById("ContentPlaceHolder1_tr_officialsite_1");
        if (element != null) {
            Elements tds = element.getElementsByTag("a");
            for (Element td : tds) {
                if (!td.attr("href").isEmpty())
                    currencyEntity.setSite(td.attr("href"));
            }
        }
    }

    private BigDecimal convertStringToBigDecimal(String number, boolean holders) {

        if (holders) {
            return new BigDecimal(number.substring(0, number.indexOf(' ')).trim().replace(',', '.'));
        }
        return new BigDecimal(number.trim().replace(',', '.'));

    }

}
