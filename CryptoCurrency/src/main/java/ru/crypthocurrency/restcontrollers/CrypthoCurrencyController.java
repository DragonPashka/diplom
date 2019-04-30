package ru.crypthocurrency.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.crypthocurrency.services.CrypthoCurrencyServices;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

import java.util.Optional;

@RestController
public class CrypthoCurrencyController
{
    @Autowired
    private CrypthoCurrencyServices crypthoCurrencyServices;

    @RequestMapping(value = "/{address}", method = RequestMethod.GET)
    public ResponseEntity<CrypthoCurrencyEntity> getCurrencyByAddress(@PathVariable("address") String address){

        Optional<CrypthoCurrencyEntity> currencyEntity = crypthoCurrencyServices.findByAddress(address);

        if(currencyEntity.isPresent()){
            return new ResponseEntity<>(currencyEntity.get(), HttpStatus.OK);
        }

        return new ResponseEntity(String.format("The currency with address = %s was not Found", address),  HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{address}", method = RequestMethod.DELETE)
    public ResponseEntity deleteCurrencyByAddress(@PathVariable("address") String address){

        if(crypthoCurrencyServices.deleteByAddress(address)){
            return new ResponseEntity(String.format("The currency with address = %s was removed", address), HttpStatus.OK);
        }

        return new ResponseEntity(String.format("The currency with address = %s is not exist", address),  HttpStatus.NOT_FOUND);
    }




}
