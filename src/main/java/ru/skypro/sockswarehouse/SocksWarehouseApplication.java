package ru.skypro.sockswarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"ru.skypro.controller", "ru.skypro.service"})
@EntityScan("ru.skypro.entity")
@EnableJpaRepositories("ru.skypro.repository")
public class SocksWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocksWarehouseApplication.class, args);
    }

}
