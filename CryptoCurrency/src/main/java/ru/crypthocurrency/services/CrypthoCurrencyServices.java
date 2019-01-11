package ru.crypthocurrency.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.crypthocurrency.repositories.CrypthoCurrencyRepository;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;


@Service
public class CrypthoCurrencyServices
{
    @Autowired
    private CrypthoCurrencyRepository crypthoCurrencyRepository;

    public CrypthoCurrencyEntity save(CrypthoCurrencyEntity entity)
    {
        return crypthoCurrencyRepository.save(entity);
    }

    public CrypthoCurrencyEntity findByAddress(String address)
    {
        return crypthoCurrencyRepository.findOne(address);
    }

    public Iterable<CrypthoCurrencyEntity> findAll()
    {
        return crypthoCurrencyRepository.findAll();
    }

    public void deleteAll()
    {
        crypthoCurrencyRepository.deleteAll();
    }

    public void addHundredEntity()
    {

    }

}
