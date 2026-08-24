package org.example.academic.system.exception;

/**
 * Exceção lançada quando o usuário fornece entrada numérica inválida (US-2368).
 */
public class InvalidNumericInputException extends KeyboardInputException {

    public InvalidNumericInputException(String message) {
        super(message);
    }

    public InvalidNumericInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
