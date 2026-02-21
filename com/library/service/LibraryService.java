package com.library.service;

import com.library.model.Book;
import com.library.model.Patron;
import com.library.repository.BookRepository;
import com.library.repository.PatronRepository;
import com.library.util.LoggingUtility;

import java.util.List;
import java.util.Optional;

public class LibraryService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    public LibraryService(BookRepository bookRepository, PatronRepository patronRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
    }

    // Book management
    public void addBook(Book book) {
        bookRepository.add(book);
        LoggingUtility.logInfo("Book added: " + book.getIsbn());
    }

    public void removeBook(String isbn) {
        bookRepository.remove(isbn);
        LoggingUtility.logInfo("Book removed: " + isbn);
    }

    public void updateBook(Book book) {
        bookRepository.update(book);
        LoggingUtility.logInfo("Book updated: " + book.getIsbn());
    }

    public Optional<Book> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public List<Book> searchBooks(String query) {
        // Simple combined search
        List<Book> byTitle = bookRepository.findByTitle(query);
        List<Book> byAuthor = bookRepository.findByAuthor(query);
        byTitle.addAll(byAuthor);
        return byTitle.stream().distinct().toList();
    }

    // Patron management
    public void addPatron(Patron patron) {
        patronRepository.add(patron);
        LoggingUtility.logInfo("Patron added: " + patron.getId());
    }

    public void updatePatron(Patron patron) {
        patronRepository.update(patron);
        LoggingUtility.logInfo("Patron updated: " + patron.getId());
    }

    public Optional<Patron> findPatronById(String id) {
        return patronRepository.findById(id);
    }

    public List<Book> searchByTitle(String title) {
    return bookRepository.findByTitle(title);
}

public List<Book> searchByAuthor(String author) {
    return bookRepository.findByAuthor(author);
}

public Optional<Book> searchByIsbn(String isbn) {
    return bookRepository.findByIsbn(isbn);
}
}