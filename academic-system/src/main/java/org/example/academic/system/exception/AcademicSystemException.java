package org.example.academic.system.exception;

/**
 * Exceção base para erros de domínio acadêmico (US-2367).
 * Todas as exceções de domínio do sistema acadêmico devem herdar desta classe.
 */
public class AcademicSystemException extends RuntimeException {

    public AcademicSystemException(String message) {
        super(message);
    }

    public AcademicSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
