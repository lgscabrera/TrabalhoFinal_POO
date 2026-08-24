package org.example.academic.system.exception;

/**
 * Exceção lançada quando a autenticação falha (US-2369).
 * Herda de SecurityException conforme AC-3.
 */
public class AuthenticationException extends SecurityException {

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
