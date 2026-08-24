package org.example.academic.system.model;
public class PracticalAssignment extends Assessment {
    public PracticalAssignment() {}
    public PracticalAssignment(Double value, Double weight) { super(value, weight); }
    @Override public String getType() { return "PracticalAssignment"; }
}
