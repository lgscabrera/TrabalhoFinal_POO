package org.example.academic.system;

import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.validation.DomainValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de validação de objetos de domínio com Jakarta Bean Validation (TUS-2385).
 * Usa classes auxiliares internas para simular AcademicClass e Assessment.
 */
class DomainValidationTest {

    // ---- Classes auxiliares para o teste ----

    @AllArgsConstructor
    static class AcademicClass {
        @NotBlank(message = "O código da turma não pode ser vazio")
        String code;

        @NotBlank(message = "O título da turma não pode ser vazio")
        String title;
    }

    @AllArgsConstructor
    static class Assessment {
        @DecimalMin(value = "0.0", message = "O valor da avaliação deve ser >= 0")
        @DecimalMax(value = "10.0", message = "O valor da avaliação deve ser <= 10")
        Double value;

        @DecimalMin(value = "0.0", inclusive = false, message = "O peso da avaliação deve ser > 0")
        @DecimalMax(value = "1.0", message = "O peso da avaliação deve ser <= 1")
        Double weight;
    }

    // ---- Testes de AcademicClass ----

    @Test
    @DisplayName("TUS-2385: Turma com dados válidos deve passar na validação")
    void validClassShouldPassValidation() {
        AcademicClass turma = new AcademicClass("BCC001", "Orientação a Objetos");
        assertDoesNotThrow(() -> DomainValidator.validate(turma));
    }

    @Test
    @DisplayName("TUS-2385: Turma com código em branco deve falhar na validação")
    void classWithBlankCodeShouldFailValidation() {
        AcademicClass turma = new AcademicClass("", "Orientação a Objetos");
        assertThrows(AcademicSystemException.class, () -> DomainValidator.validate(turma));
    }

    @Test
    @DisplayName("TUS-2385: Turma com título em branco deve falhar na validação")
    void classWithBlankTitleShouldFailValidation() {
        AcademicClass turma = new AcademicClass("BCC001", "   ");
        assertThrows(AcademicSystemException.class, () -> DomainValidator.validate(turma));
    }

    // ---- Testes de Assessment ----

    @Test
    @DisplayName("TUS-2385: Avaliação com valor inválido deve falhar na validação")
    void assessmentWithInvalidValueShouldFailValidation() {
        Assessment assessment = new Assessment(-1.0, 0.3);
        assertThrows(AcademicSystemException.class, () -> DomainValidator.validate(assessment));
    }

    @Test
    @DisplayName("TUS-2385: Avaliação com peso inválido deve falhar na validação")
    void assessmentWithInvalidWeightShouldFailValidation() {
        Assessment assessment = new Assessment(8.0, 1.5);
        assertThrows(AcademicSystemException.class, () -> DomainValidator.validate(assessment));
    }

    @Test
    @DisplayName("TUS-2385: Avaliação com dados válidos deve passar na validação")
    void validAssessmentShouldPassValidation() {
        Assessment assessment = new Assessment(8.0, 0.4);
        assertDoesNotThrow(() -> DomainValidator.validate(assessment));
    }
}
