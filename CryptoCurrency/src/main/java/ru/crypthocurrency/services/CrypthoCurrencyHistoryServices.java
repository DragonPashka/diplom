package ru.crypthocurrency.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.crypthocurrency.repositories.CrypthoCurrencyHistoryRepository;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;
import ru.crypthocurrency.tables.CrypthoCurrencyHistoryEntity;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CrypthoCurrencyHistoryServices {

    @Autowired
    private CrypthoCurrencyServices crypthoCurrencyServices;

    @Autowired
    private CrypthoCurrencyHistoryRepository crypthoCurrencyHistoryRepository;

    @Getter
    private Map<CrypthoCurrencyEntity, List<CrypthoCurrencyHistoryEntity>> cashedHistory;

    @Getter
    private List<String> cashedDate;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void init(){
        cashedHistory = new HashMap<>();
        cashedDate = new ArrayList<>();
        CrypthoCurrencyEntity entity=new CrypthoCurrencyEntity();
        entity.setAddress("123");
        entity.setShortName("CurrencyName");

        List<CrypthoCurrencyHistoryEntity> list=new ArrayList<>(100);
        for(int i=0; i<100; i++){
            CrypthoCurrencyHistoryEntity historyEntity = new CrypthoCurrencyHistoryEntity();
            historyEntity.setCurrencyEntity(entity);
            historyEntity.setVolumeTraded(new BigDecimal(i));
            historyEntity.setTradesCount(new BigDecimal(i));
            historyEntity.setPriceUSD(new BigDecimal(i));

            Calendar currentDate = Calendar.getInstance();
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE, -100+i);
            historyEntity.setDate(date.getTime());
            list.add(historyEntity);
        }
        cashedHistory.put(entity, list);
        restrictedData();

    }



    public List<CrypthoCurrencyHistoryEntity> findAllHistoryByAddress(String address) {
        Optional<CrypthoCurrencyEntity> entityOptional = crypthoCurrencyServices.findByAddress(address);

        if (!entityOptional.isPresent()) {
            return null;
        }

        cashedHistory.put(entityOptional.get(), crypthoCurrencyHistoryRepository.findCrypthoCurrencyHistoryEntityByCurrencyEntity(entityOptional.get()));
        restrictedData();
        return cashedHistory.get(entityOptional.get());

    }

    private void restrictedData() {

        int min = -1;

        for (CrypthoCurrencyEntity key : cashedHistory.keySet()) {
            if (min == -1) {
                min = cashedHistory.get(key).size();
                continue;
            }

            if (min > cashedHistory.get(key).size()){
                min = cashedHistory.get(key).size();
            }
        }

        for (CrypthoCurrencyEntity key : cashedHistory.keySet()) {
            List<CrypthoCurrencyHistoryEntity> list = cashedHistory.get(key);
            List<CrypthoCurrencyHistoryEntity> removeList = new ArrayList<>(50);
           if (min != list.size()){
               for (int i=0; i<list.size(); i++) {
                   if(i<list.size()-min){
                       removeList.add(list.get(i));
                   } else break;
               }
               list.removeAll(removeList);
               cashedHistory.put(key, list);
            }
           else if (cashedDate.size()< min) {
               for (int i=0; i<list.size(); i++){
                   cashedDate.add(dateFormat.format(list.get(i).getDate()));
               }

           }
        }

    }

}
