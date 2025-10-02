package com.ampuero.msvc.inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@SpringBootApplication
public class MsvcInventarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcInventarioApplication.class, args);
    }

}
