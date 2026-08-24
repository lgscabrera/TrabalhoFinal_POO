package org.example.academic.system.exception;

/**
 * Exceção base para erros de segurança (US-2369).
 * Separada das exceções de domínio acadêmico e de entrada por teclado conforme AC-4.
 */
public class SecurityException extends RuntimeException {

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
