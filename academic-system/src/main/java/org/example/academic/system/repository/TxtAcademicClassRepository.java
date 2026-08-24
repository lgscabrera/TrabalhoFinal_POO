package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Repositório que persiste turmas e avaliações em arquivo TXT (TUS-2362).
 * Formato: código,título,tipoAvaliação,valor,peso — uma linha por avaliação.
 */
public class TxtAcademicClassRepository implements AcademicClassRepository {

    private static final Logger logger = LoggerFactory.getLogger(TxtAcademicClassRepository.class);
    private static final String FILE_PATH = "academic_data.txt";

    @Override
    public void save(List<AcademicClass> classes) {
        logger.info("Iniciando persistência em TXT. Arquivo: '{}'", FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (AcademicClass academicClass : classes) {
                List<Assessment> assessments = academicClass.getAssessments();
                if (assessments.isEmpty()) {
                    writer.write(academicClass.getCode() + "," + academicClass.getTitle() + ",,,");
                    writer.newLine();
                } else {
                    for (Assessment assessment : assessments) {
                        writer.write(
                            academicClass.getCode() + "," +
                            academicClass.getTitle() + "," +
                            assessment.getType() + "," +
                            assessment.getValue() + "," +
                            assessment.getWeight()
                        );
                        writer.newLine();
                    }
                }
            }
            logger.info("Persistência em TXT concluída com sucesso. {} turma(s) salva(s).", classes.size());
        } catch (IOException e) {
            logger.error("Erro ao salvar dados em TXT: {}", e.getMessage());
            throw new RuntimeException("Falha ao salvar dados no arquivo TXT: " + e.getMessage(), e);
        }
    }
}
