package ru.crypthocurrency.services;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.crypthocurrency.coinapi.CoinApi;
import ru.crypthocurrency.etherscan.EtherScanData;
import ru.crypthocurrency.repositories.CrypthoCurrencyRepository;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

import java.util.List;
import java.util.Optional;


@Service
@Log
public class CrypthoCurrencyServices
{

    @Autowired
    private CrypthoCurrencyRepository crypthoCurrencyRepository;

    @Autowired
    private CoinApi coinApi;

    @Autowired
    private EtherScanData etherScanData;

    public Optional<CrypthoCurrencyEntity> findByAddress(String address){
        return crypthoCurrencyRepository.findById(address);
    }

    public CrypthoCurrencyEntity save(CrypthoCurrencyEntity entity) {

        try {
            if(!crypthoCurrencyRepository.findById(entity.getAddress()).isPresent()) {
                etherScanData.setHoldersAndTransfers(entity);
                etherScanData.setOfficialSite(entity);
                CrypthoCurrencyEntity savedEntity = crypthoCurrencyRepository.save(entity);

                //Проставляем исторические данные за последние полгода
                coinApi.setHistoricalData(entity.getAddress(), entity.getShortName());

                return savedEntity;
            }

        } catch (Exception e) {
            log.warning("Error while saving the currency to DB");
        }

        return null;
    }



    public List<CrypthoCurrencyEntity> findAll()  {
        return crypthoCurrencyRepository.findAll();
    }

    public boolean deleteByAddress(String address){

        if(crypthoCurrencyRepository.findById(address).isPresent()){
            crypthoCurrencyRepository.deleteById(address);
            if(!crypthoCurrencyRepository.findById(address).isPresent())
                return true;
            return false;
        }

        return false;
    }

    public void deleteAll() {
        crypthoCurrencyRepository.deleteAll();
    }
}
