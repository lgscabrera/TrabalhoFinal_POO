package org.example.academic.system;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.TurmaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TUS-2402 — Test AssessmentService behavior.
 */
class AssessmentServiceTest {

    private AssessmentService assessmentService;
    private AcademicSystem academicSystem;
    private static final String CLASS_CODE = "BCC-ASSMT-TST";

    @BeforeEach
    void setUp() {
        academicSystem = AcademicSystem.getInstance();
        assessmentService = new AssessmentService(academicSystem);
        // Ensure test class exists
        if (academicSystem.findByCode(CLASS_CODE).isEmpty()) {
            new TurmaService(academicSystem).registerClass(CLASS_CODE, "Classe de Teste");
        }
    }

    @Test
    @DisplayName("TUS-2402: Deve registrar avaliação em turma existente")
    void shouldRegisterAssessmentInExistingClass() {
        AcademicClass before = academicSystem.findByCode(CLASS_CODE).orElseThrow();
        int count = before.getAssessments().size();
        assessmentService.registerAssessment(CLASS_CODE, "EXAM", 7.0, 0.5);
        assertEquals(count + 1, before.getAssessments().size());
    }

    @Test
    @DisplayName("TUS-2402: Tipo de avaliação inválido não deve adicionar avaliação")
    void invalidTypeShouldNotAddAssessment() {
        AcademicClass turma = academicSystem.findByCode(CLASS_CODE).orElseThrow();
        int count = turma.getAssessments().size();
        assessmentService.registerAssessment(CLASS_CODE, "INVALID_TYPE", 7.0, 0.5);
        assertEquals(count, turma.getAssessments().size());
    }

    @Test
    @DisplayName("TUS-2402: Código de turma inexistente não deve adicionar avaliação")
    void nonExistentClassShouldNotAddAssessment() {
        // Should not throw, just silently ignore
        assertDoesNotThrow(() ->
            assessmentService.registerAssessment("NAOEXISTE", "EXAM", 7.0, 0.5));
    }
}
