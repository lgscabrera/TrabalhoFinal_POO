package org.example.academic.system.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.academic.system.exception.AcademicSystemException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Componente centralizador de validação de objetos de domínio usando Jakarta Bean Validation.
 * Implementa TUS-2371.
 * Converte violações de validação em AcademicSystemException (AC-7).
 */
public class DomainValidator {

    private static final Validator validator;

    static {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    /**
     * Valida o objeto fornecido.
     * Lança AcademicSystemException se houver violações (TUS-2371, AC-7).
     *
     * @param object objeto a ser validado
     * @param <T>    tipo do objeto
     */
    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            String messages = violations.stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining("; "));
            throw new AcademicSystemException("Dados inválidos: " + messages);
        }
    }
}
