package org.example.academic.system.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.academic.system.model.AcademicClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Repositório que persiste turmas e avaliações em arquivo JSON (US-2374).
 * Usa Jackson para serialização.
 */
public class JsonAcademicClassRepository implements AcademicClassRepository {

    private static final Logger logger = LoggerFactory.getLogger(JsonAcademicClassRepository.class);
    private static final String FILE_PATH = "academic_data.json";

    private final ObjectMapper objectMapper;

    public JsonAcademicClassRepository() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void save(List<AcademicClass> classes) {
        logger.info("Iniciando persistência em JSON. Arquivo: '{}'", FILE_PATH);

        try {
            objectMapper.writeValue(new File(FILE_PATH), classes);
            logger.info("Persistência em JSON concluída com sucesso. {} turma(s) salva(s).", classes.size());
        } catch (Exception e) {
            logger.error("Erro ao salvar dados em JSON: {}", e.getMessage());
            throw new RuntimeException("Falha ao salvar dados no arquivo JSON: " + e.getMessage(), e);
        }
    }
}
