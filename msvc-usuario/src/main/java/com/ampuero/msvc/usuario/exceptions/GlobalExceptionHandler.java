package com.ampuero.msvc.usuario.exceptions;

import com.ampuero.msvc.usuario.dtos.ErrorDTO;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para msvc-usuario
 * Intercepta y maneja todas las excepciones del microservicio
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Crea un ErrorDTO con los datos proporcionados
     */
    private ErrorDTO createErrorDTO(int status, LocalDate localDate, Map<String, String> errorMap) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(status);
        errorDTO.setLocalDate(localDate);
        errorDTO.setErrors(errorMap);
        return errorDTO;
    }

    /**
     * Maneja errores de validación de campos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationFields(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST.value(), localDate, errorMap));
    }

    /**
     * Maneja excepciones cuando no se encuentra un recurso
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> errorMap = Collections.singletonMap("error", ex.getMessage());
        LocalDate localDate = LocalDate.now();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(this.createErrorDTO(HttpStatus.NOT_FOUND.value(), localDate, errorMap));
    }

    /**
     * Maneja excepciones de recursos duplicados
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDTO> handleDuplicateResource(DuplicateResourceException ex) {
        Map<String, String> errorMap = Collections.singletonMap("error", ex.getMessage());
        LocalDate localDate = LocalDate.now();
        
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(this.createErrorDTO(HttpStatus.CONFLICT.value(), localDate, errorMap));
    }

    /**
     * Maneja excepciones de Feign (comunicación con otros microservicios)
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDTO> handleFeignException(FeignException ex) {
        Map<String, String> errorMap = Collections.singletonMap("error", 
            "Error de comunicación con servicio externo: " + ex.getMessage());
        LocalDate localDate = LocalDate.now();
        
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(this.createErrorDTO(HttpStatus.BAD_GATEWAY.value(), localDate, errorMap));
    }

    /**
     * Maneja excepciones específicas de usuario
     */
    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ErrorDTO> handleUsuarioException(UsuarioException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // Default
        
        if (ex.getMessage().toLowerCase().contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().toLowerCase().contains("ya existe") || 
                   ex.getMessage().toLowerCase().contains("conflicto")) {
            status = HttpStatus.CONFLICT;
        } else if (ex.getMessage().toLowerCase().contains("inválido") || 
                   ex.getMessage().toLowerCase().contains("incorrecto")) {
            status = HttpStatus.BAD_REQUEST;
        }
        
        Map<String, String> errorMap = Collections.singletonMap("error", ex.getMessage());
        LocalDate localDate = LocalDate.now();
        
        return ResponseEntity.status(status)
                .body(this.createErrorDTO(status.value(), localDate, errorMap));
    }

    /**
     * Maneja excepciones genéricas no controladas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {
        Map<String, String> errorMap = Collections.singletonMap("error", 
            "Error interno del servidor: " + ex.getMessage());
        LocalDate localDate = LocalDate.now();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(this.createErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), localDate, errorMap));
    }

    /**
     * Maneja argumentos ilegales
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> errorMap = Collections.singletonMap("error", 
            "Argumento inválido: " + ex.getMessage());
        LocalDate localDate = LocalDate.now();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST.value(), localDate, errorMap));
    }

    /**
     * Maneja excepciones de puntero nulo
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorDTO> handleNullPointerException(NullPointerException ex) {
        Map<String, String> errorMap = Collections.singletonMap("error", 
            "Error de referencia nula en el usuario");
        LocalDate localDate = LocalDate.now();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(this.createErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), localDate, errorMap));
    }

    /**
     * Maneja errores de validación de JPA/Hibernate (ConstraintViolationException)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            errorMap.put(propertyPath, message);
        }
        
        // Si no hay errores específicos, agregar mensaje genérico
        if (errorMap.isEmpty()) {
            errorMap.put("error", "Error de validación: " + ex.getMessage());
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST.value(), localDate, errorMap));
    }

    /**
     * Maneja errores de integridad de datos (violaciones de constraints de BD)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        LocalDate localDate = LocalDate.now();
        
        String message = ex.getMessage();
        if (message != null) {
            // Intentar extraer información útil del mensaje
            if (message.contains("duplicate key") || message.contains("UNIQUE constraint")) {
                if (message.contains("correo") || message.contains("email")) {
                    errorMap.put("correo", "Ya existe un usuario con este correo electrónico");
                } else if (message.contains("run") || message.contains("RUN")) {
                    errorMap.put("runUsuario", "Ya existe un usuario con este RUN");
                } else if (message.contains("codigo_referido")) {
                    errorMap.put("codigoReferido", "Ya existe un usuario con este código de referido");
                } else {
                    errorMap.put("error", "Ya existe un registro con estos datos");
                }
            } else if (message.contains("NOT NULL constraint")) {
                errorMap.put("error", "Faltan campos obligatorios: " + message);
            } else {
                errorMap.put("error", "Error de integridad de datos: " + message);
            }
        } else {
            errorMap.put("error", "Error de integridad de datos");
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(this.createErrorDTO(HttpStatus.BAD_REQUEST.value(), localDate, errorMap));
    }
}
