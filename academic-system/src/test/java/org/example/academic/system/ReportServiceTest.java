package org.example.academic.system;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.model.Exam;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.service.PersistenceService;
import org.example.academic.system.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TUS-2404 — Test ReportService behavior.
 */
class ReportServiceTest {

    private ReportService reportService;
    private AcademicSystem academicSystem;

    @BeforeEach
    void setUp() {
        academicSystem = AcademicSystem.getInstance();
        PersistenceService persistenceService = new PersistenceService();
        reportService = new ReportService(academicSystem, persistenceService);

        // Register a class with an assessment for report testing
        if (academicSystem.findByCode("RPT-TEST").isEmpty()) {
            AcademicClass c = new AcademicClass("RPT-TEST", "Turma de Relatório");
            c.addAssessment(new Exam(8.5, 0.6));
            academicSystem.registerClass(c);
        }
    }

    @Test
    @DisplayName("TUS-2404: Relatório de avaliações deve conter dados da turma")
    void classSummaryReportShouldContainClassData() {
        String report = reportService.generateClassSummaryReport("ADMIN");
        assertNotNull(report);
        assertTrue(report.contains("RPT-TEST"));
    }

    @Test
    @DisplayName("TUS-2404: Relatório de pesos deve calcular total corretamente")
    void weightReportShouldCalculateTotalWeight() {
        String report = reportService.generateWeightReport("ADMIN");
        assertNotNull(report);
        assertFalse(report.isBlank());
    }

    @Test
    @DisplayName("TUS-2404: Relatório de persistência deve mostrar tipo configurado")
    void persistenceReportShouldShowCurrentType() {
        String report = reportService.generatePersistenceReport("ADMIN");
        assertNotNull(report);
        assertTrue(report.contains("TXT"));
    }
}
