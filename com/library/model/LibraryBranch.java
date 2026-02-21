package com.library.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LibraryBranch {
    private final String branchId;
    private String name;
    private String address;
    private List<BookItem> inventory;

    public LibraryBranch(String name, String address) {
        this.branchId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.inventory = new ArrayList<>();
    }

    // Getters and setters
    public String getBranchId() { return branchId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<BookItem> getInventory() { return inventory; }
}