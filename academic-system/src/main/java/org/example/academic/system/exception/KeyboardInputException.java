package org.example.academic.system.exception;

/**
 * Exceção base para erros de entrada por teclado (US-2368).
 * Separada das exceções de domínio e de segurança conforme AC-5.
 */
public class KeyboardInputException extends RuntimeException {

    public KeyboardInputException(String message) {
        super(message);
    }

    public KeyboardInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
