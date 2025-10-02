package com.ampuero.msvc.promociones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcPromocionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcPromocionesApplication.class, args);
    }

}
