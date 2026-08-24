package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.repository.PersistenceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Serviço responsável pela geração de relatórios (TUS-2399).
 */
public class ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
    private final AcademicSystem academicSystem;
    private final PersistenceService persistenceService;

    public ReportService(AcademicSystem academicSystem, PersistenceService persistenceService) {
        this.academicSystem = academicSystem;
        this.persistenceService = persistenceService;
    }

    /**
     * Relatório de avaliações por turma (US-2375).
     */
    public String generateClassSummaryReport(String userRole) {
        logger.info("Gerando relatório de avaliações por turma. Papel: {}", userRole);
        List<AcademicClass> classes = academicSystem.getClasses();
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE AVALIAÇÕES POR TURMA ===\n\n");
        if (classes.isEmpty()) {
            sb.append("Nenhuma turma registrada.\n");
        } else {
            for (AcademicClass c : classes) {
                sb.append("Turma: ").append(c.getCode()).append(" - ").append(c.getTitle()).append("\n");
                if (c.getAssessments().isEmpty()) {
                    sb.append("  Sem avaliações.\n");
                } else {
                    for (Assessment a : c.getAssessments()) {
                        sb.append(String.format("  [%s] Valor: %.1f | Peso: %.2f%n",
                            a.getType(), a.getValue(), a.getWeight()));
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Relatório de peso das avaliações (US-2376).
     */
    public String generateWeightReport(String userRole) {
        logger.info("Gerando relatório de pesos. Papel: {}", userRole);
        List<AcademicClass> classes = academicSystem.getClasses();
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE PESOS DAS AVALIAÇÕES ===\n\n");
        if (classes.isEmpty()) {
            sb.append("Nenhuma turma registrada.\n");
        } else {
            for (AcademicClass c : classes) {
                double totalWeight = c.getAssessments().stream()
                    .mapToDouble(Assessment::getWeight).sum();
                String valid = Math.abs(totalWeight - 1.0) < 0.001 ? "VÁLIDA" : "INVÁLIDA";
                sb.append(String.format("Turma: %s - %s%n  Peso total: %.2f — Composição: %s%n%n",
                    c.getCode(), c.getTitle(), totalWeight, valid));
            }
        }
        return sb.toString();
    }

    /**
     * Relatório de configuração de persistência (US-2377).
     */
    public String generatePersistenceReport(String userRole) {
        logger.info("Gerando relatório de persistência. Papel: {}", userRole);
        PersistenceType type = persistenceService.getCurrentType();
        return "=== RELATÓRIO DE CONFIGURAÇÃO DE PERSISTÊNCIA ===\n\n" +
               "Tipo de persistência atual: " + type.name() + "\n";
    }
}
