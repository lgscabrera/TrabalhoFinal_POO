package org.example.academic.system;

import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.service.TurmaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TUS-2401 — Test ClassService (TurmaService) behavior.
 */
class TurmaServiceTest {

    private TurmaService turmaService;
    private AcademicSystem academicSystem;

    @BeforeEach
    void setUp() {
        // Use a fresh instance via reflection or just use the singleton  
        academicSystem = AcademicSystem.getInstance();
        turmaService = new TurmaService(academicSystem);
    }

    @Test
    @DisplayName("TUS-2401: ClassService deve registrar turma válida")
    void shouldRegisterValidClass() {
        int before = academicSystem.getClasses().size();
        turmaService.registerClass("BCC-TEST-" + System.nanoTime(), "Test Class");
        assertTrue(academicSystem.getClasses().size() > before);
    }

    @Test
    @DisplayName("TUS-2401: Código em branco deve lançar AcademicSystemException")
    void blankCodeShouldThrow() {
        assertThrows(AcademicSystemException.class,
            () -> turmaService.registerClass("", "Título válido"));
    }

    @Test
    @DisplayName("TUS-2401: Título em branco deve lançar AcademicSystemException")
    void blankTitleShouldThrow() {
        assertThrows(AcademicSystemException.class,
            () -> turmaService.registerClass("BCC001", ""));
    }
}
