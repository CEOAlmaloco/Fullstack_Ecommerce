package com.ampuero.msvc.pedido.exceptions;

import com.ampuero.msvc.pedido.dtos.ErrorDTO;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ErrorDTO createErrorDTO(int status, LocalDate localDate, Map<String, String> errorMap) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(status);
        errorDTO.setLocalDate(localDate);
        errorDTO.setErrors(errorMap);
        return errorDTO;
    }

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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> errorMap = Collections.singletonMap("error", ex.getMessage());
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(this.createErrorDTO(HttpStatus.NOT_FOUND.value(), localDate, errorMap));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDTO> handleFeignException(FeignException ex) {
        Map<String, String> errorMap = Collections.singletonMap("error", "Error de comunicación con servicio externo: " + ex.getMessage());
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(this.createErrorDTO(HttpStatus.BAD_GATEWAY.value(), localDate, errorMap));
    }

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<ErrorDTO> handleClienteException(ClienteException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // Default
        if (ex.getMessage().toLowerCase().contains("no encontrado")) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex.getMessage().toLowerCase().contains("ya existe") || ex.getMessage().toLowerCase().contains("conflicto")) {
            status = HttpStatus.CONFLICT;
        } else if (ex.getMessage().toLowerCase().contains("inválido") || ex.getMessage().toLowerCase().contains("incorrecto")) {
            status = HttpStatus.BAD_REQUEST;
        }
        Map<String, String> errorMap = Collections.singletonMap("error", ex.getMessage());
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.status(status)
                .body(this.createErrorDTO(status.value(), localDate, errorMap));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {
        logger.error("Error no manejado en el servidor", ex);
        
        String errorMessage = ex.getMessage();
        if (errorMessage == null || errorMessage.isEmpty()) {
            errorMessage = "Error interno del servidor: " + ex.getClass().getSimpleName();
        }
        
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", errorMessage);
        errorMap.put("type", ex.getClass().getSimpleName());
        
        // Incluir mensaje de causa si existe
        if (ex.getCause() != null) {
            errorMap.put("cause", ex.getCause().getMessage());
        }
        
        LocalDate localDate = LocalDate.now();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(this.createErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), localDate, errorMap));
    }
}