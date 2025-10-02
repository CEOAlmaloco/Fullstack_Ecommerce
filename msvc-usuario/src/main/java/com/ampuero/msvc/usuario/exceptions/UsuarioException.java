package com.ampuero.msvc.usuario.exceptions;

/**
 * Excepción personalizada para errores relacionados con usuarios
 * Se lanza cuando ocurren errores específicos del dominio de usuario
 */
public class UsuarioException extends RuntimeException {

    public UsuarioException(String message) {
        super(message);
    }

    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioException(Throwable cause) {
        super(cause);
    }
}
