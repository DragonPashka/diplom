package ru.crypthocurrency.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.crypthocurrency.services.CrypthoCurrencyHistoryServices;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;
import ru.crypthocurrency.tables.CrypthoCurrencyHistoryEntity;

import java.util.List;

@Controller
public class CrypthoCurrencyHistoryController {

    @Autowired
    private CrypthoCurrencyHistoryServices crypthoCurrencyHistoryServices;

    @RequestMapping(value = "/history", method = RequestMethod.POST)
    public String findAllHistoryByAddress(@RequestParam("address") String address) {

        System.out.println(address);
        crypthoCurrencyHistoryServices.getCashedHistory().clear();
        crypthoCurrencyHistoryServices.getCashedDate().clear();
        List<CrypthoCurrencyHistoryEntity> resultSet = crypthoCurrencyHistoryServices.findAllHistoryByAddress(address);

        if (resultSet == null) {
            return String.format("The history about currency with address = %s was not Found", address);
        }

        return "redirect:/Cryptho/CurrencyWithChart";
    }

    @RequestMapping(value = "/addCurrencyToGraphics", method = RequestMethod.POST)
    public String addCurrencyToGraphicsByAddress(@RequestParam("address") String address) {

        boolean defaultValue=false;
        for (CrypthoCurrencyEntity key: crypthoCurrencyHistoryServices.getCashedHistory().keySet()){
            if (key.getAddress().equals("123")){
                defaultValue=true;
                break;
            }
        }
        if(defaultValue){
            crypthoCurrencyHistoryServices.getCashedDate().clear();
            crypthoCurrencyHistoryServices.getCashedHistory().clear();
        }

        List<CrypthoCurrencyHistoryEntity> resultSet = crypthoCurrencyHistoryServices.findAllHistoryByAddress(address);

        if (resultSet == null) {
            return String.format("The history about currency with address = %s was not Found", address);
        }

        return "redirect:/Cryptho/CurrencyWithChart";
    }

}
