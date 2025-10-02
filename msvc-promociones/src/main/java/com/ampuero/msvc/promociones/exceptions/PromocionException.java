package com.ampuero.msvc.promociones.exceptions;

public class PromocionException extends RuntimeException {
    public PromocionException(String message) {
        super(message);
    }
    
    public PromocionException(String message, Throwable cause) {
        super(message, cause);
    }
}
