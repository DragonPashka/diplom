package ru.crypthocurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.crypthocurrency.coinapi.CoinApi;

import javax.annotation.PostConstruct;
import java.text.ParseException;

@SpringBootApplication
public class Main
{

    @Autowired
    private CoinApi coinApi;

    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
    }

    @PostConstruct
    public void fun() throws ParseException {
        coinApi.getHistoricalData();
    }
}
