package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.repository.AcademicClassRepository;
import org.example.academic.system.repository.JsonAcademicClassRepository;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.repository.TxtAcademicClassRepository;
import org.example.academic.system.repository.XmlAcademicClassRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Serviço responsável por isolar a lógica de persistência (TUS-2398).
 * Gerencia a troca dinâmica de tipo de persistência (US-2372).
 * Registra todas as operações de persistência em log (TUS-2393).
 */
public class PersistenceService {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceService.class);

    private PersistenceType currentType;
    private AcademicClassRepository repository;

    /**
     * Inicializa o serviço com TXT como persistência padrão.
     */
    public PersistenceService() {
        this.currentType = PersistenceType.TXT;
        this.repository = new TxtAcademicClassRepository();
        logger.info("PersistenceService inicializado. Tipo padrão: {}", currentType);
    }

    /**
     * Salva os dados acadêmicos usando o repositório atualmente configurado.
     * Registra a operação em log (TUS-2393).
     *
     * @param classes lista de turmas a serem salvas
     */
    public void save(List<AcademicClass> classes) {
        logger.info("Iniciando operação de salvamento. Tipo de persistência ativo: {}", currentType);
        repository.save(classes);
        logger.info("Operação de salvamento concluída. Tipo: {}, {} turma(s) processada(s).",
            currentType, classes.size());
    }

    /**
     * Altera o tipo de persistência ativo (US-2372).
     * Registra a troca em log (TUS-2393).
     *
     * @param type novo tipo de persistência
     */
    public void setPersistenceType(PersistenceType type) {
        logger.info("Alterando tipo de persistência de {} para {}.", currentType, type);
        this.currentType = type;
        this.repository = switch (type) {
            case TXT  -> new TxtAcademicClassRepository();
            case XML  -> new XmlAcademicClassRepository();
            case JSON -> new JsonAcademicClassRepository();
        };
        logger.info("Tipo de persistência alterado com sucesso para {}.", currentType);
    }

    /**
     * Retorna o tipo de persistência atualmente configurado.
     *
     * @return tipo de persistência ativo
     */
    public PersistenceType getCurrentType() {
        return currentType;
    }

    /**
     * Retorna o repositório atualmente ativo.
     * Usado principalmente para fins de teste.
     *
     * @return repositório ativo
     */
    public AcademicClassRepository getRepository() {
        return repository;
    }
}
