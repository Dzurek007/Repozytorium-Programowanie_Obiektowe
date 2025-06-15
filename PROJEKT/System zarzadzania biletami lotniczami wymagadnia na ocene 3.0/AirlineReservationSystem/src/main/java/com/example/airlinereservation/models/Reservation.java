package com.example.airlinereservation.models;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int passengerId;
    private int flightId;
    private LocalDate reservationDate;
    private int seats;

    public Reservation(int id, int passengerId, int flightId,
                       LocalDate reservationDate, int seats) {
        this.id = id;
        this.passengerId = passengerId;
        this.flightId = flightId;
        this.reservationDate = reservationDate;
        this.seats = seats;
    }
    // gettery/settery
    public int getId() { return id; }
    public int getPassengerId() { return passengerId; }
    public int getFlightId() { return flightId; }
    public LocalDate getReservationDate() { return reservationDate; }
    public int getSeats() { return seats; }
}
