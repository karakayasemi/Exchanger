package com.openpayd.exchanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.openpayd.exchanger"})
public class ExchangerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangerApplication.class, args);
    }

}
