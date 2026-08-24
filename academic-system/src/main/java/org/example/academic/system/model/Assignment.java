package org.example.academic.system.model;
public class Assignment extends Assessment {
    public Assignment() {}
    public Assignment(Double value, Double weight) { super(value, weight); }
    @Override public String getType() { return "Assignment"; }
}
