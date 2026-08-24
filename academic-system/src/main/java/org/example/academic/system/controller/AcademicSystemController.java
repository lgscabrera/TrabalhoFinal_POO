package org.example.academic.system.controller;

import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.exception.AuthorizationException;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.model.Role;
import org.example.academic.system.model.Turma;
import org.example.academic.system.model.User;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.PersistenceService;
import org.example.academic.system.service.ReportService;
import org.example.academic.system.service.TurmaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador principal — delega operações aos serviços (TUS-2400).
 */
public class AcademicSystemController {

    private static final Logger logger = LoggerFactory.getLogger(AcademicSystemController.class);

    private final TurmaService turmaService;
    private final AssessmentService assessmentService;
    private final PersistenceService persistenceService;
    private final ReportService reportService;

    public AcademicSystemController() {
        AcademicSystem system = AcademicSystem.getInstance();
        this.persistenceService = new PersistenceService();
        this.turmaService = new TurmaService(system);
        this.assessmentService = new AssessmentService(system);
        this.reportService = new ReportService(system, persistenceService);
    }

    public AcademicSystemController(AcademicSystem academicSystem) {
        this.persistenceService = new PersistenceService();
        this.turmaService = new TurmaService(academicSystem);
        this.assessmentService = new AssessmentService(academicSystem);
        this.reportService = new ReportService(academicSystem, persistenceService);
    }

    // For injection in tests
    public AcademicSystemController(TurmaService turmaService,
                                    AssessmentService assessmentService,
                                    PersistenceService persistenceService,
                                    ReportService reportService) {
        this.turmaService = turmaService;
        this.assessmentService = assessmentService;
        this.persistenceService = persistenceService;
        this.reportService = reportService;
    }

    /** US-2363 */
    public void registerClass(User user, String code, String title) {
        requireAdmin(user, "cadastrar turma");
        turmaService.registerClass(code, title);
    }

    /** US-2361 */
    public void registerAssessment(User user, String classCode,
                                   String type, double value, double weight) {
        assessmentService.registerAssessment(classCode, type, value, weight);
    }

    /** US-2372 */
    public void configurePersistence(User user, String type) {
        requireAdmin(user, "configurar persistência");
        persistenceService.setPersistenceType(PersistenceType.valueOf(type.toUpperCase()));
    }

    /** Save data */
    public void saveData(User user) {
        requireAdmin(user, "salvar dados");
        persistenceService.save(AcademicSystem.getInstance().getClasses());
    }

    /** US-2375 */
    public String generateClassSummaryReport(User user) {
        return reportService.generateClassSummaryReport(user.getRole().name());
    }

    /** US-2376 */
    public String generateWeightReport(User user) {
        return reportService.generateWeightReport(user.getRole().name());
    }

    /** US-2377 */
    public String generatePersistenceReport(User user) {
        requireAdmin(user, "relatório de persistência");
        return reportService.generatePersistenceReport(user.getRole().name());
    }

    private void requireAdmin(User user, String operation) {
        if (user == null || user.getRole() != Role.ADMIN) {
            String role = user != null ? user.getRole().name() : "SEM SESSÃO";
            logger.warn("Acesso negado. Papel: {}. Operação: {}", role, operation);
            throw new AuthorizationException(
                "Acesso negado. Esta operação requer o papel: ADMIN.");
        }
    }
}
