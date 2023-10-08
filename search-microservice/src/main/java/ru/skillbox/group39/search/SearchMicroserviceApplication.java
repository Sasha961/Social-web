package ru.skillbox.group39.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class SearchMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchMicroserviceApplication.class, args);
    }

}
