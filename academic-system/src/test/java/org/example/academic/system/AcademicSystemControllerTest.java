package org.example.academic.system;

import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.PersistenceService;
import org.example.academic.system.service.ReportService;
import org.example.academic.system.service.TurmaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TUS-2405 — Test AcademicSystemController delegation behavior.
 */
@ExtendWith(MockitoExtension.class)
class AcademicSystemControllerTest {

    @Mock private TurmaService turmaService;
    @Mock private AssessmentService assessmentService;
    @Mock private PersistenceService persistenceService;
    @Mock private ReportService reportService;

    private AcademicSystemController controller;
    private User adminUser;
    private User professorUser;

    @BeforeEach
    void setUp() {
        controller = new AcademicSystemController(
            turmaService, assessmentService, persistenceService, reportService);
        adminUser = new User("admin", "admin123", Role.ADMIN);
        professorUser = new User("prof", "prof123", Role.PROFESSOR);
    }

    @Test
    @DisplayName("TUS-2405: Controller deve delegar registerClass ao TurmaService")
    void shouldDelegateRegisterClassToService() {
        controller.registerClass(adminUser, "BCC001", "POO");
        verify(turmaService).registerClass("BCC001", "POO");
    }

    @Test
    @DisplayName("TUS-2405: PROFESSOR não pode registrar turma")
    void professorShouldNotRegisterClass() {
        assertThrows(AuthorizationException.class,
            () -> controller.registerClass(professorUser, "BCC001", "POO"));
        verifyNoInteractions(turmaService);
    }

    @Test
    @DisplayName("TUS-2405: Controller deve delegar registerAssessment ao AssessmentService")
    void shouldDelegateRegisterAssessmentToService() {
        controller.registerAssessment(adminUser, "BCC001", "EXAM", 8.0, 0.5);
        verify(assessmentService).registerAssessment("BCC001", "EXAM", 8.0, 0.5);
    }
}
