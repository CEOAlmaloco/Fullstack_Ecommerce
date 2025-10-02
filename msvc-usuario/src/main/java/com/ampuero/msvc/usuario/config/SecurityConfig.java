package com.ampuero.msvc.usuario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración de seguridad para msvc-usuario
 * Define beans necesarios para el manejo de contraseñas y seguridad
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean para encriptación de contraseñas
     * @return PasswordEncoder configurado
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}