package com.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Patron {
    private final String id;
    private String name;
    private String email;
    private List<Loan> borrowingHistory;

    public Patron(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.borrowingHistory = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Loan> getBorrowingHistory() { return borrowingHistory; }
    public void addLoan(Loan loan) { borrowingHistory.add(loan); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patron patron = (Patron) o;
        return Objects.equals(id, patron.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}