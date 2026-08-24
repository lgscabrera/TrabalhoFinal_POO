package org.example.academic.system.exception;

/**
 * Exceção lançada quando o acesso a uma operação protegida é negado (US-2369).
 * Herda de SecurityException conforme AC-3.
 */
public class AuthorizationException extends SecurityException {

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
