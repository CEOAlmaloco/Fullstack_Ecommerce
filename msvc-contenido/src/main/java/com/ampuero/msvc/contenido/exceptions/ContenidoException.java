package com.ampuero.msvc.contenido.exceptions;

public class ContenidoException extends RuntimeException {
    public ContenidoException(String message) {
        super(message);
    }
    
    public ContenidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
