package com.ampuero.msvc.referidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ReferidosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReferidosApplication.class, args);
    }

}
