package com.ampuero.msvc.contenido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MsvcContenidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcContenidoApplication.class, args);
    }

}
