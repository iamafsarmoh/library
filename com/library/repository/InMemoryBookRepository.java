package com.library.repository;

import com.library.model.Book;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> storage = new ConcurrentHashMap<>();

    @Override
    public void add(Book book) {
        storage.put(book.getIsbn(), book);
    }

    @Override
    public void remove(String isbn) {
        storage.remove(isbn);
    }

    @Override
    public void update(Book book) {
        storage.put(book.getIsbn(), book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(storage.get(isbn));
    }

    @Override
    public List<Book> findByTitle(String title) {
        return storage.values().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return storage.values().stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}