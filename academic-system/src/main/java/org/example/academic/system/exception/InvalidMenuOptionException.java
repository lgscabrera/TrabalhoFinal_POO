package org.example.academic.system.exception;

/**
 * Exceção lançada quando o usuário seleciona uma opção de menu inválida (US-2368).
 */
public class InvalidMenuOptionException extends KeyboardInputException {

    public InvalidMenuOptionException(String message) {
        super(message);
    }

    public InvalidMenuOptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
