package com.tcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StarprotectVehicleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarprotectVehicleApplication.class, args);
    }
}
