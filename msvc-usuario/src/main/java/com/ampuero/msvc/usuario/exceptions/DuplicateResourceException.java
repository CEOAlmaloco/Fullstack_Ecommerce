package com.ampuero.msvc.usuario.exceptions;

/**
 * Excepción que se lanza cuando se intenta crear un recurso que ya existe
 * Específicamente para correos duplicados, códigos de referido duplicados, etc.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateResourceException(Throwable cause) {
        super(cause);
    }
}
