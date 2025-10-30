package br.com.tp.lncr.customerorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.tp.lncr")
public class AppCustomerOrder {

    public static void main(String[] args) {
        SpringApplication.run(AppCustomerOrder.class, args);
    }
}
