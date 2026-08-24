package org.example.academic.system.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

/**
 * Classe base para avaliações acadêmicas (US-2361).
 * Pode ser instanciada diretamente com tipo como string, ou via subclasses tipadas.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type", defaultImpl = Assessment.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Exam.class, name = "Exam"),
    @JsonSubTypes.Type(value = PracticalAssignment.class, name = "PracticalAssignment"),
    @JsonSubTypes.Type(value = Seminar.class, name = "Seminar"),
    @JsonSubTypes.Type(value = Assignment.class, name = "Assignment")
})
public class Assessment {

    private String type;

    @DecimalMin(value = "0.0", message = "O valor da avaliação deve ser >= 0")
    @DecimalMax(value = "10.0", message = "O valor da avaliação deve ser <= 10")
    private Double value;

    @DecimalMin(value = "0.0", inclusive = false, message = "O peso da avaliação deve ser > 0")
    @DecimalMax(value = "1.0", message = "O peso da avaliação deve ser <= 1")
    private Double weight;

    /** No-arg for Jackson */
    public Assessment() {}

    /** Constructor used directly in tests: new Assessment("Exam", 8.5, 0.6) */
    public Assessment(String type, Double value, Double weight) {
        this.type = type;
        this.value = value;
        this.weight = weight;
    }

    /** Constructor for subclasses */
    protected Assessment(Double value, Double weight) {
        this.value = value;
        this.weight = weight;
    }

    public Double getValue() { return value; }
    public Double getWeight() { return weight; }

    public String getType() {
        return type != null ? type : this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return getType() + "{value=" + value + ", weight=" + weight + "}";
    }
}
