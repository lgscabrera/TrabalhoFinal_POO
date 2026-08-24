package org.example.academic.system;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.repository.AcademicClassRepository;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.service.PersistenceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes do PersistenceService (TUS-2403).
 * Verifica salvar, trocar tipo e geração de arquivos corretos.
 */
class PersistenceServiceTest {

    private PersistenceService persistenceService;
    private List<AcademicClass> classes;

    @BeforeEach
    void setUp() {
        persistenceService = new PersistenceService();
        AcademicClass turma = new AcademicClass("BCC001", "POO");
        classes = List.of(turma);
    }

    @AfterEach
    void cleanUp() {
        new File("academic_data.txt").delete();
        new File("academic_data.xml").delete();
        new File("academic_data.json").delete();
    }

    @Test
    @DisplayName("TUS-2403: Tipo padrão deve ser TXT")
    void defaultPersistenceTypeShouldBeTxt() {
        assertEquals(PersistenceType.TXT, persistenceService.getCurrentType());
    }

    @Test
    @DisplayName("TUS-2403: Salvar com TXT padrão deve gerar arquivo TXT")
    void saveShouldGenerateTxtFileByDefault() {
        persistenceService.save(classes);
        assertTrue(new File("academic_data.txt").exists());
    }

    @Test
    @DisplayName("TUS-2403: Arquivo TXT deve conter dados da turma")
    void txtFileShouldContainClassData() throws Exception {
        persistenceService.save(classes);
        String content = Files.readString(Path.of("academic_data.txt"));
        assertTrue(content.contains("BCC001"));
    }

    @Test
    @DisplayName("TUS-2403: Alterar para XML deve gerar arquivo XML")
    void changingToXmlShouldGenerateXmlFile() {
        persistenceService.setPersistenceType(PersistenceType.XML);
        assertEquals(PersistenceType.XML, persistenceService.getCurrentType());
        persistenceService.save(classes);
        assertTrue(new File("academic_data.xml").exists());
    }

    @Test
    @DisplayName("TUS-2403: Alterar para JSON deve gerar arquivo JSON")
    void changingToJsonShouldGenerateJsonFile() {
        persistenceService.setPersistenceType(PersistenceType.JSON);
        assertEquals(PersistenceType.JSON, persistenceService.getCurrentType());
        persistenceService.save(classes);
        assertTrue(new File("academic_data.json").exists());
    }

    @Test
    @DisplayName("TUS-2403: Repositório deve ser atualizado ao trocar tipo")
    void repositoryShouldBeUpdatedOnTypeChange() {
        AcademicClassRepository before = persistenceService.getRepository();
        persistenceService.setPersistenceType(PersistenceType.XML);
        AcademicClassRepository after = persistenceService.getRepository();
        assertNotSame(before, after);
    }
}
