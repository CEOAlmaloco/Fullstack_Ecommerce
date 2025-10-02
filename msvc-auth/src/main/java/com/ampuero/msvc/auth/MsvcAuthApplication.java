package com.ampuero.msvc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@SpringBootApplication
@EntityScan("com.ampuero.msvc.auth.models")
@EnableJpaRepositories("com.ampuero.msvc.auth.repositories")
public class MsvcAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsvcAuthApplication.class, args);
    }

}
