package org.example.academic.system;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.repository.JsonAcademicClassRepository;
import org.example.academic.system.repository.TxtAcademicClassRepository;
import org.example.academic.system.repository.XmlAcademicClassRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes dos repositórios de persistência (US-2389).
 * Verifica que os arquivos gerados contêm os dados esperados.
 */
class PersistenceRepositoryTest {

    @TempDir
    Path tempDir;

    private List<AcademicClass> classes;

    @BeforeEach
    void setUp() {
        Assessment exam = new Assessment("Exam", 8.5, 0.6);
        Assessment work = new Assessment("PracticalAssignment", 7.0, 0.4);

        AcademicClass turma = new AcademicClass("BCC001", "Orientação a Objetos");
        turma.addAssessment(exam);
        turma.addAssessment(work);

        classes = List.of(turma);
    }

    @AfterEach
    void cleanUp() {
        // Remove arquivos gerados pelo diretório de trabalho padrão, se existirem
        new File("academic_data.txt").delete();
        new File("academic_data.xml").delete();
        new File("academic_data.json").delete();
    }

    // ---- TXT ----

    @Test
    @DisplayName("US-2389: Repositório TXT deve gerar arquivo")
    void txtRepositoryShouldGenerateFile() {
        new TxtAcademicClassRepository().save(classes);
        assertTrue(new File("academic_data.txt").exists());
    }

    @Test
    @DisplayName("US-2389: Arquivo TXT deve conter código e título da turma")
    void txtFileShouldContainClassData() throws Exception {
        new TxtAcademicClassRepository().save(classes);
        String content = Files.readString(Path.of("academic_data.txt"));
        assertTrue(content.contains("BCC001"));
        assertTrue(content.contains("Orientação a Objetos"));
    }

    @Test
    @DisplayName("US-2389: Arquivo TXT deve conter dados das avaliações")
    void txtFileShouldContainAssessmentData() throws Exception {
        new TxtAcademicClassRepository().save(classes);
        String content = Files.readString(Path.of("academic_data.txt"));
        assertTrue(content.contains("Exam"));
        assertTrue(content.contains("8.5"));
        assertTrue(content.contains("0.6"));
    }

    // ---- XML ----

    @Test
    @DisplayName("US-2389: Repositório XML deve gerar arquivo")
    void xmlRepositoryShouldGenerateFile() {
        new XmlAcademicClassRepository().save(classes);
        assertTrue(new File("academic_data.xml").exists());
    }

    @Test
    @DisplayName("US-2389: Arquivo XML deve conter dados da turma")
    void xmlFileShouldContainClassData() throws Exception {
        new XmlAcademicClassRepository().save(classes);
        String content = Files.readString(Path.of("academic_data.xml"));
        assertTrue(content.contains("BCC001"));
        assertTrue(content.contains("Orientação a Objetos"));
    }

    @Test
    @DisplayName("US-2389: Arquivo XML deve conter dados das avaliações")
    void xmlFileShouldContainAssessmentData() throws Exception {
        new XmlAcademicClassRepository().save(classes);
        String content = Files.readString(Path.of("academic_data.xml"));
        assertTrue(content.contains("Exam"));
        assertTrue(content.contains("8.5"));
        assertTrue(content.contains("0.6"));
    }

    // ---- JSON ----

    @Test
    @DisplayName("US-2389: Repositório JSON deve gerar arquivo")
    void jsonRepositoryShouldGenerateFile() {
        new JsonAcademicClassRepository().save(classes);
        assertTrue(new File("academic_data.json").exists());
    }

    @Test
    @DisplayName("US-2389: Arquivo JSON deve conter dados da turma")
    void jsonFileShouldContainClassData() throws Exception {
        new JsonAcademicClassRepository().save(classes);
        String content = Files.readString(Path.of("academic_data.json"));
        assertTrue(content.contains("BCC001"));
        assertTrue(content.contains("Orientação a Objetos"));
    }

    @Test
    @DisplayName("US-2389: Arquivo JSON deve conter dados das avaliações")
    void jsonFileShouldContainAssessmentData() throws Exception {
        new JsonAcademicClassRepository().save(classes);
        String content = Files.readString(Path.of("academic_data.json"));
        assertTrue(content.contains("Exam"));
        assertTrue(content.contains("8.5"));
        assertTrue(content.contains("0.6"));
    }
}
