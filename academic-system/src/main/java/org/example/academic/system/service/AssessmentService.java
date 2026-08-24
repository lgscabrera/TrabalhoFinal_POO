package org.example.academic.system.service;

import org.example.academic.system.exception.AcademicSystemException;
import org.example.academic.system.exception.AssessmentException;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.model.Assignment;
import org.example.academic.system.model.Exam;
import org.example.academic.system.model.PracticalAssignment;
import org.example.academic.system.model.Seminar;
import org.example.academic.system.validation.DomainValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Serviço responsável pelo registro de avaliações (TUS-2397).
 */
public class AssessmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentService.class);
    private final AcademicSystem academicSystem;

    public AssessmentService() {
        this.academicSystem = AcademicSystem.getInstance();
    }

    public AssessmentService(AcademicSystem academicSystem) {
        this.academicSystem = academicSystem;
    }

    /**
     * Registra uma avaliação na turma indicada pelo código (US-2361).
     */
    public void registerAssessment(String classCode, String assessmentType,
                                   Double value, Double weight) {
        Optional<AcademicClass> optClass = academicSystem.findByCode(classCode);
        if (optClass.isEmpty()) {
            logger.warn("Tentativa de registrar avaliação em turma inexistente: {}", classCode);
            return; // AC6/AC7: não adiciona se turma não existe
        }

        Assessment assessment = createAssessment(assessmentType, value, weight);
        if (assessment == null) {
            logger.warn("Tipo de avaliação inválido: {}", assessmentType);
            return; // AC5/AC6: tipo inválido não adiciona
        }

        DomainValidator.validate(assessment);
        optClass.get().addAssessment(assessment);
        logger.info("Avaliação {} registrada na turma {}.", assessmentType, classCode);
    }

    private Assessment createAssessment(String type, Double value, Double weight) {
        return switch (type.toUpperCase()) {
            case "EXAM" -> new Exam(value, weight);
            case "PRACTICAL_ASSIGNMENT" -> new PracticalAssignment(value, weight);
            case "SEMINAR" -> new Seminar(value, weight);
            case "ASSIGNMENT" -> new Assignment(value, weight);
            default -> null;
        };
    }
}
