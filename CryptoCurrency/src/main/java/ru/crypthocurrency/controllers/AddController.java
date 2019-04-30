package ru.crypthocurrency.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.crypthocurrency.services.CrypthoCurrencyHistoryServices;
import ru.crypthocurrency.services.CrypthoCurrencyServices;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

@Controller
public class AddController {
    @Autowired
    private CrypthoCurrencyServices crypthoCurrencyServices;

    @Autowired
    private CrypthoCurrencyHistoryServices crypthoCurrencyHistoryServices;

    @RequestMapping(value = "/addCurrency", method = RequestMethod.POST)
    public String addCurrency(CrypthoCurrencyEntity currency) {
        CrypthoCurrencyEntity currencyEntity = crypthoCurrencyServices.save(currency);
        if (currencyEntity != null) {
            crypthoCurrencyHistoryServices.getCashedDate().clear();
            crypthoCurrencyHistoryServices.getCashedHistory().clear();
            crypthoCurrencyHistoryServices.findAllHistoryByAddress(currencyEntity.getAddress());

            return "redirect:/Cryptho/CurrencyWithChart";
        }
        return String.format("Error while add currency with address = %s", currency.getAddress());
    }

    @RequestMapping(value = "/Cryptho/AllCurrenciesTable", method = RequestMethod.GET)
    public String allCurrencies(Model model) {
        model.addAttribute("currencies", crypthoCurrencyServices.findAll());

        return "allcurrencies";
    }
}
