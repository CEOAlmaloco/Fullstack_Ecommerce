package com.ampuero.msvc.contenido.exceptions;

import com.ampuero.msvc.contenido.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContenidoException.class)
    public ResponseEntity<ErrorDTO> handleContenidoException(ContenidoException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setLocalDate(LocalDate.now());
        
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        error.setErrors(errors);
        
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setLocalDate(LocalDate.now());
        
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        error.setErrors(errors);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setLocalDate(LocalDate.now());
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        error.setErrors(errors);
        
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenericException(Exception ex) {
        ErrorDTO error = new ErrorDTO();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setLocalDate(LocalDate.now());
        
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Error interno del servidor: " + ex.getMessage());
        error.setErrors(errors);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
