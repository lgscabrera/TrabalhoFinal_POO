package org.example.academic.system.service;

import org.example.academic.system.exception.ClassRegistrationException;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.AcademicSystem;
import org.example.academic.system.validation.DomainValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Serviço responsável pelo registro de turmas (TUS-2396).
 */
public class TurmaService {

    private static final Logger logger = LoggerFactory.getLogger(TurmaService.class);
    private final AcademicSystem academicSystem;

    public TurmaService() {
        this.academicSystem = AcademicSystem.getInstance();
    }

    public TurmaService(AcademicSystem academicSystem) {
        this.academicSystem = academicSystem;
    }

    /**
     * Registra uma turma no sistema (US-2363).
     */
    public void registerClass(String code, String title) {
        AcademicClass academicClass = new AcademicClass(code, title);
        DomainValidator.validate(academicClass);
        academicSystem.registerClass(academicClass);
        logger.info("Turma registrada: {} - {}", code, title);
    }
}
