package com.library.model;

import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    private final String loanId;
    private BookItem bookItem;
    private Patron patron;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(BookItem bookItem, Patron patron, int loanPeriodDays) {
        this.loanId = UUID.randomUUID().toString();
        this.bookItem = bookItem;
        this.patron = patron;
        this.checkoutDate = LocalDate.now();
        this.dueDate = checkoutDate.plusDays(loanPeriodDays);
    }

    // Getters and setters
    public String getLoanId() { return loanId; }
    public BookItem getBookItem() { return bookItem; }
    public Patron getPatron() { return patron; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public boolean isReturned() { return returnDate != null; }
}