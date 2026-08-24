package org.example.academic.system.model;
public class Exam extends Assessment {
    public Exam() {}
    public Exam(Double value, Double weight) { super(value, weight); }
    @Override public String getType() { return "Exam"; }
}
