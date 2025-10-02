package com.ampuero.msvc.resenia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class MsvcReseniaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcReseniaApplication.class, args);
    }

}
