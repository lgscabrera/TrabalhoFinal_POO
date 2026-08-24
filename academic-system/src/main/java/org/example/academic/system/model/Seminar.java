package org.example.academic.system.model;
public class Seminar extends Assessment {
    public Seminar() {}
    public Seminar(Double value, Double weight) { super(value, weight); }
    @Override public String getType() { return "Seminar"; }
}
