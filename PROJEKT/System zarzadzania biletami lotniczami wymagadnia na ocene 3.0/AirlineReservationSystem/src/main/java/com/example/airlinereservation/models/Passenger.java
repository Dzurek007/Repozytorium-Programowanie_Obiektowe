package com.example.airlinereservation.models;

public class Passenger extends Person {
    protected String passport;

    public Passenger(int id, String firstName, String lastName, String passport) {
        super(id, firstName, lastName);
        this.passport = passport;
    }
    public String getPassport() { return passport; }
    public void setPassport(String passport) { this.passport = passport; }
}
