package com.library.model;

import java.util.UUID;

public class BookItem {
    private final String barcode;
    private Book book;
    private LibraryBranch currentBranch;
    private boolean isBorrowed;

    public BookItem(Book book, LibraryBranch branch) {
        this.barcode = UUID.randomUUID().toString();
        this.book = book;
        this.currentBranch = branch;
        this.isBorrowed = false;
    }

    // Getters and setters
    public String getBarcode() { return barcode; }
    public Book getBook() { return book; }
    public LibraryBranch getCurrentBranch() { return currentBranch; }
    public void setCurrentBranch(LibraryBranch branch) { this.currentBranch = branch; }
    public boolean isBorrowed() { return isBorrowed; }
    public void setBorrowed(boolean borrowed) { isBorrowed = borrowed; }
}