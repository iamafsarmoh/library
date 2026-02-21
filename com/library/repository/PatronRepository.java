package com.library.repository;

import com.library.model.Patron;
import java.util.List;
import java.util.Optional;

public interface PatronRepository {
    void add(Patron patron);
    void update(Patron patron);
    Optional<Patron> findById(String id);
    List<Patron> findAll();
}