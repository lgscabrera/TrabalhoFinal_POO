package org.example.academic.system.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa uma turma acadêmica (US-2363).
 * Igualdade baseada no código (TUS-2382).
 */
@Getter
public class AcademicClass {

    @NotBlank(message = "O código da turma não pode ser vazio")
    private final String code;

    @NotBlank(message = "O título da turma não pode ser vazio")
    private final String title;

    private final List<Assessment> assessments = new ArrayList<>();

    public AcademicClass(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public void addAssessment(Assessment assessment) {
        this.assessments.add(assessment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcademicClass that)) return false;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "AcademicClass{code='" + code + "', title='" + title + "'}";
    }
}
