package com.example.airlinereservation.models;

import java.time.LocalDateTime;

public class Flight {
    private int id;
    private String flightNumber, origin, destination;
    private LocalDateTime departureTime, arrivalTime;

    public Flight(int id, String flightNumber, String origin, String destination,
                  LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
    // gettery/settery...
    public int getId() { return id; }
    public String getFlightNumber() { return flightNumber; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
    public LocalDateTime getDepartureTime() { return departureTime; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }
}
