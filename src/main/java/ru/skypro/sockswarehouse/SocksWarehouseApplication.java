package ru.skypro.sockswarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"ru.skypro.controller","ru.skypro.service"})
@SpringBootApplication
public class SocksWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocksWarehouseApplication.class, args);
    }

}
