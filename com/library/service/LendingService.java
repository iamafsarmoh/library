package com.library.service;

import com.library.exception.BookNotAvailableException;
import com.library.exception.BookNotFoundException;
import com.library.exception.PatronNotFoundException;
import com.library.model.*;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import com.library.repository.PatronRepository;
import com.library.util.LoggingUtility;

import java.time.LocalDate;
import java.util.Optional;

public class LendingService {
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;
    private final LoanRepository loanRepository;
    private final int loanPeriodDays = 14; // configurable

    public LendingService(BookRepository bookRepository, PatronRepository patronRepository,
                          LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.loanRepository = loanRepository;
    }

    public Loan checkoutBook(String isbn, String patronId, LibraryBranch branch) 
            throws BookNotFoundException, PatronNotFoundException, BookNotAvailableException {
        // Find book and patron
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found: " + isbn));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found: " + patronId));

        // Find an available copy at the branch
        BookItem availableItem = branch.getInventory().stream()
                .filter(item -> item.getBook().equals(book) && !item.isBorrowed())
                .findFirst()
                .orElseThrow(() -> new BookNotAvailableException("No available copy of " + book.getTitle()));

        // Create loan
        availableItem.setBorrowed(true);
        Loan loan = new Loan(availableItem, patron, loanPeriodDays);
        patron.addLoan(loan);
        loanRepository.save(loan);

        LoggingUtility.logInfo("Book checked out: " + isbn + " to patron " + patronId);
        return loan;
    }

    public void returnBook(String barcode) {
        // Find loan by book item barcode (simplified: we search all active loans)
        Optional<Loan> activeLoan = loanRepository.findAllActive().stream()
                .filter(loan -> loan.getBookItem().getBarcode().equals(barcode))
                .findFirst();

        if (activeLoan.isPresent()) {
            Loan loan = activeLoan.get();
            loan.setReturnDate(LocalDate.now());
            loan.getBookItem().setBorrowed(false);
            LoggingUtility.logInfo("Book returned: " + barcode);
        } else {
            LoggingUtility.logWarning("Attempt to return non-borrowed book: " + barcode);
        }
    }
}