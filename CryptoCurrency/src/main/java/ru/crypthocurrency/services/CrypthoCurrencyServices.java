package ru.crypthocurrency.services;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.crypthocurrency.repositories.CrypthoCurrencyRepository;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static ru.crypthocurrency.model.Restrictions.setHoldersAndTransfers;
import static ru.crypthocurrency.model.Restrictions.setOfficialSite;


@Service
@Log
public class CrypthoCurrencyServices
{
    @Autowired
    private CrypthoCurrencyRepository crypthoCurrencyRepository;

    public Optional<CrypthoCurrencyEntity> findByAddress(String address){
        return crypthoCurrencyRepository.findById(address);
    }

    public CrypthoCurrencyEntity save(CrypthoCurrencyEntity entity) {

        try {
            if(!crypthoCurrencyRepository.findById(entity.getAddress()).isPresent()) {
                setHoldersAndTransfers(entity);
                setOfficialSite(entity);
                return crypthoCurrencyRepository.save(entity);
            }

        } catch (IOException e) {
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
