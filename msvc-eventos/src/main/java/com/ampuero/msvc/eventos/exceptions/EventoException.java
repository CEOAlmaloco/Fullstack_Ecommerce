package com.ampuero.msvc.eventos.exceptions;

public class EventoException extends RuntimeException {
    public EventoException(String message) {
        super(message);
    }
    
    public EventoException(String message, Throwable cause) {
        super(message, cause);
    }
}
