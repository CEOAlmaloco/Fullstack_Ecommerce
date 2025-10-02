package com.ampuero.msvc.usuario.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

/**
 * DTO para manejo de errores
 * Contiene información sobre errores que ocurren en la aplicación
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private int status;
    private LocalDate localDate;
    private Map<String, String> errors;
    private String message;
    private String path;
    private String timestamp;
    
    /**
     * Constructor para errores simples
     */
    public ErrorDTO(int status, String message) {
        this.status = status;
        this.message = message;
        this.localDate = LocalDate.now();
    }
    
    /**
     * Constructor para errores con mapa de errores
     */
    public ErrorDTO(int status, Map<String, String> errors) {
        this.status = status;
        this.errors = errors;
        this.localDate = LocalDate.now();
    }
}
