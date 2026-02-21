package com.library.service;

import com.library.exception.BookNotFoundException;
import com.library.exception.PatronNotFoundException;
import com.library.model.Book;
import com.library.model.Patron;
import com.library.model.Reservation;
import com.library.observer.BookAvailableNotifier;
import com.library.observer.PatronNotifier;
import com.library.repository.BookRepository;
import com.library.repository.PatronRepository;
import com.library.util.LoggingUtility;

import java.util.*;

public class ReservationService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final BookAvailableNotifier notifier;
    private final Map<Book, Queue<Reservation>> reservationQueue = new HashMap<>();

    public ReservationService(BookRepository bookRepository, PatronRepository patronRepository,
                              BookAvailableNotifier notifier) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.notifier = notifier;
    }

    public void reserveBook(String isbn, String patronId) 
            throws BookNotFoundException, PatronNotFoundException {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found: " + isbn));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found: " + patronId));

        Reservation reservation = new Reservation(book, patron);
        reservationQueue.computeIfAbsent(book, k -> new LinkedList<>()).add(reservation);

        // Subscribe patron to notifications for this book
        notifier.subscribe(new PatronNotifier(patron));

        LoggingUtility.logInfo("Book reserved: " + isbn + " for patron " + patronId);
    }

    public void fulfillNextReservation(Book book) {
        Queue<Reservation> queue = reservationQueue.get(book);
        if (queue != null && !queue.isEmpty()) {
            Reservation next = queue.poll();
            next.setFulfilled(true);
            notifier.notifyBookAvailable(book);
            LoggingUtility.logInfo("Notifying next patron for book: " + book.getIsbn());
        }
    }
}