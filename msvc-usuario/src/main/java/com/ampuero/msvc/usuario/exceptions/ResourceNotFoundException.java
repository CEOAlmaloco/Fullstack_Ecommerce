package com.ampuero.msvc.usuario.exceptions;

/**
 * Excepción que se lanza cuando no se encuentra un recurso
 * Específicamente para usuarios, direcciones o preferencias no encontradas
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
