package ru.crypthocurrency.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

@Controller
public class GreetingController {

    @GetMapping("/Cryptho/Greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping("/Add")
    public String add(@ModelAttribute CrypthoCurrencyEntity crypthoCurrencyEntity) {
        return "add";
    }

}
