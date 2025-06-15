package com.example.airlinereservation.models;

public class FrequentPassenger extends Passenger {
    protected String frequentFlyerNumber;
    protected int miles;

    public FrequentPassenger(int id, String firstName, String lastName, String passport,
                             String frequentFlyerNumber, int miles) {
        super(id, firstName, lastName, passport);
        this.frequentFlyerNumber = frequentFlyerNumber;
        this.miles = miles;
    }
    public String getFrequentFlyerNumber() { return frequentFlyerNumber; }
    public int getMiles() { return miles; }
}
