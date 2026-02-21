package com.library.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Reservation {
    private final String reservationId;
    private Book book;
    private Patron patron;
    private LocalDateTime reservationTime;
    private boolean fulfilled;

    public Reservation(Book book, Patron patron) {
        this.reservationId = UUID.randomUUID().toString();
        this.book = book;
        this.patron = patron;
        this.reservationTime = LocalDateTime.now();
        this.fulfilled = false;
    }

    // Getters and setters
    public String getReservationId() { return reservationId; }
    public Book getBook() { return book; }
    public Patron getPatron() { return patron; }
    public LocalDateTime getReservationTime() { return reservationTime; }
    public boolean isFulfilled() { return fulfilled; }
    public void setFulfilled(boolean fulfilled) { this.fulfilled = fulfilled; }
}