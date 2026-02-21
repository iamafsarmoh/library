package com.library.repository;

import com.library.model.Patron;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPatronRepository implements PatronRepository {
    private final Map<String, Patron> storage = new ConcurrentHashMap<>();

    @Override
    public void add(Patron patron) {
        storage.put(patron.getId(), patron);
    }

    @Override
    public void update(Patron patron) {
        storage.put(patron.getId(), patron);
    }

    @Override
    public Optional<Patron> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Patron> findAll() {
        return new ArrayList<>(storage.values());
    }
}