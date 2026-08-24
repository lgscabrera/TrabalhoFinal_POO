package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;

import java.util.List;

/**
 * Interface do repositório de turmas acadêmicas.
 * Define o contrato de persistência (padrão Strategy/Repository).
 * Implementações concretas: TXT, XML e JSON (TUS-2362, US-2373, US-2374).
 */
public interface AcademicClassRepository {

    /**
     * Salva a lista de turmas no formato de persistência configurado.
     *
     * @param classes lista de turmas a serem salvas
     */
    void save(List<AcademicClass> classes);
}
