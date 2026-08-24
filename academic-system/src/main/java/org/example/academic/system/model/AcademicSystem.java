package org.example.academic.system.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Singleton que representa o estado do sistema acadêmico (US-0000).
 */
public class AcademicSystem {

    private static AcademicSystem instance;
    private final List<AcademicClass> classes = new ArrayList<>();

    private AcademicSystem() {}

    public static AcademicSystem getInstance() {
        if (instance == null) {
            instance = new AcademicSystem();
        }
        return instance;
    }

    public void registerClass(AcademicClass academicClass) {
        classes.add(academicClass);
    }

    public List<AcademicClass> getClasses() {
        return List.copyOf(classes);
    }

    public Optional<AcademicClass> findByCode(String code) {
        return classes.stream()
            .filter(c -> c.getCode().equals(code))
            .findFirst();
    }
}
