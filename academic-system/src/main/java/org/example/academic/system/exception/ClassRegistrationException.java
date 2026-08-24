package org.example.academic.system.exception;

/**
 * Exceção lançada quando ocorrem erros no registro ou manipulação de turmas (US-2367).
 */
public class ClassRegistrationException extends AcademicSystemException {

    public ClassRegistrationException(String message) {
        super(message);
    }

    public ClassRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
