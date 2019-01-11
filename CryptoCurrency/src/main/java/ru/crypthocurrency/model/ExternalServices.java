package ru.crypthocurrency.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

import java.io.IOException;

import java.math.BigDecimal;
import java.util.*;

import static ru.crypthocurrency.model.Restrictions.*;

public class ExternalServices
{
    private static String urlEthafaction="https://ethfaction.github.io/erc20_tokens/mainnet/symbol_to_contract/";

    public static ArrayList<CrypthoCurrencyEntity> getHundredEntityes() throws IOException, JSONException {
        ArrayList<CrypthoCurrencyEntity> list = new ArrayList<>();
        list.ensureCapacity(100);

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate.getForEntity(urlEthafaction, String.class);
        HashMap<String, String> result =objectMapper.readValue(response.getBody(), HashMap.class);

        List<String> names = new ArrayList<String>(result.keySet());
        int i=0;

        while(list.size()!=10) {
            CrypthoCurrencyEntity entity = new CrypthoCurrencyEntity();
            entity.setName(names.get(i));
            entity.setAddress(result.get(names.get(i)));

            System.out.println("name:  "+ names.get(i));
            entity = getValuableCurrency(entity);
            if (entity != null){
                list.add(entity);
                i++;
            }

        }
        return list;

    }


    public static CrypthoCurrencyEntity getValuableCurrency(CrypthoCurrencyEntity entity) throws IOException, JSONException {

        entity=isRegisteredCoinBase(entity);
        entity=setHoldersAndTransfers(entity);
        entity=setOfficialSite(entity);
        //entity=AverageTenTrasactionInMonth(entity);
        if (entity.getHolders().compareTo(new BigDecimal(100))==-1 || entity.getTransfers().compareTo(new BigDecimal(100))==-1)//|| entity.getAverage() == null)
            return null;

        return entity;
    }



//totaltxns
    public static void main(String[] args) throws IOException, JSONException {

        CrypthoCurrencyEntity entity = new CrypthoCurrencyEntity();
        entity.setAddress("0xB8c77482e45F1F44dE1745F52C74426C631bDD52");
        System.out.println(getValuableCurrency(entity));


    }

}
