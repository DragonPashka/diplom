package ru.crypthocurrency.coinapi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.crypthocurrency.repositories.CrypthoCurrencyHistoryRepository;
import ru.crypthocurrency.repositories.CrypthoCurrencyRepository;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;
import ru.crypthocurrency.tables.CrypthoCurrencyHistoryEntity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class CoinApi {

    //2016-01-01T00:00:00
    private String historicalData="https://rest.coinapi.io/v1/ohlcv/%s/USD/history?period_id=1DAY&time_start=%s&time_end=%s";
    private String coinAPIKey="6AB7F8F9-6785-4A1A-A825-6E2ED1082873";

    @Autowired
    private CrypthoCurrencyHistoryRepository crypthoCurrencyHistoryRepository;

    @Autowired
    private CrypthoCurrencyRepository crypthoCurrencyRepository;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");





    private void getValuesFromJsonArray(String jsonString, String id) throws ParseException {
        JSONArray jsonArray = new JSONArray(jsonString);

        CrypthoCurrencyEntity currencyEntity = crypthoCurrencyRepository.findById(id).get();



        for(int i=0; i<jsonArray.length(); i++){
            CrypthoCurrencyHistoryEntity entity = new CrypthoCurrencyHistoryEntity();
            entity.setCurrencyEntity(currencyEntity);
            entity.setDate(dateFormat.parse(((JSONObject)jsonArray.get(i)).optString("time_period_start")));
            entity.setPriceUSD(new BigDecimal(((JSONObject)jsonArray.get(i)).optString("price_close")));
            entity.setTradesCount(new BigDecimal(((JSONObject)jsonArray.get(i)).optString("trades_count")));
            entity.setVolumeTraded(new BigDecimal(((JSONObject)jsonArray.get(i)).optString("volume_traded")));
            crypthoCurrencyHistoryRepository.save(entity);

        }
    }

    private List<String> getDateForHalfYear(){
        List<String> calendars = new ArrayList<>(2);
        Calendar currentDate = Calendar.getInstance();
        Calendar date = Calendar.getInstance();
        setMidnight(date);
        setMidnight(currentDate);

        date.add(Calendar.MONTH, -6);
        calendars.add(dateFormat.format(date.getTime()));
        calendars.add(dateFormat.format(currentDate.getTime()));

        return calendars;





    }
    public void  getHistoricalData() throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-CoinAPI-Key", "6AB7F8F9-6785-4A1A-A825-6E2ED1082873");
        HttpEntity entity = new HttpEntity(headers);

        List<String> dates = getDateForHalfYear();

        String url= String.format(historicalData, "BNB", dates.get(0), dates.get(1));
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        getValuesFromJsonArray(response.getBody(), "0xB8c77482e45F1F44dE1745F52C74426C631bDD52");
    }


    private void setMidnight(Calendar calendar){

        calendar.add(Calendar.DATE, 1);  // number of days to add
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

    }





}
