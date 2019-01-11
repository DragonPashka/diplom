package ru.crypthocurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import ru.crypthocurrency.repositories.CrypthoCurrencyRepository;

@SpringBootApplication
public class Main extends SpringBootServletInitializer
{
    private static Class<Main> applicationClass = Main.class;

    @Autowired
    private CrypthoCurrencyRepository crypthoCurrencyRepository;

    public static void main(String[] args)
    {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(applicationClass);
    }
}
