package ru.crypthocurrency.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.crypthocurrency.services.CrypthoCurrencyHistoryServices;
import ru.crypthocurrency.services.CrypthoCurrencyServices;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class GreetingController {

    @Autowired
    private CrypthoCurrencyServices crypthoCurrencyServices;

    @Autowired
    private CrypthoCurrencyHistoryServices crypthoCurrencyHistoryServices;

    @GetMapping("/Cryptho/Greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/Cryptho/Add")
    public String add(@ModelAttribute CrypthoCurrencyEntity crypthoCurrencyEntity) {
        return "add";
    }

    @GetMapping("/Cryptho/CurrencyWithChart")
    public String currencyWithChart(Model model) {

        model.addAttribute("currencies", crypthoCurrencyServices.findAll());
        model.addAttribute("cashedDate", crypthoCurrencyHistoryServices.getCashedDate());
        model.addAttribute("currenciesMap", crypthoCurrencyHistoryServices.getCashedHistory());

        return "currencywithchart";
    }
    @PostConstruct
    public void init(){
        crypthoCurrencyHistoryServices.init();
    }

}
