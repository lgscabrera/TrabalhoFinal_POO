package org.example.academic.system.exception;

/**
 * Exceção lançada quando ocorrem erros no registro ou manipulação de avaliações (US-2367).
 */
public class AssessmentException extends AcademicSystemException {

    public AssessmentException(String message) {
        super(message);
    }

    public AssessmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
