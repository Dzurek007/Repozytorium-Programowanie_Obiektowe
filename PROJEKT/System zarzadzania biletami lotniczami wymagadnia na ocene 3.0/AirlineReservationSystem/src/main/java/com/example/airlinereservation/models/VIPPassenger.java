package com.example.airlinereservation.models;

public class VIPPassenger extends FrequentPassenger {
    private String vipLevel;

    public VIPPassenger(int id, String firstName, String lastName, String passport,
                        String frequentFlyerNumber, int miles, String vipLevel) {
        super(id, firstName, lastName, passport, frequentFlyerNumber, miles);
        this.vipLevel = vipLevel;
    }
    public String getVipLevel() { return vipLevel; }
}
