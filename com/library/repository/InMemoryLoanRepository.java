package com.library.repository;

import com.library.model.Loan;
import com.library.model.BookItem;
import com.library.model.Patron;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryLoanRepository implements LoanRepository {
    private final Map<String, Loan> storage = new ConcurrentHashMap<>();

    @Override
    public void save(Loan loan) {
        storage.put(loan.getLoanId(), loan);
    }

    @Override
    public Optional<Loan> findActiveLoanByBookItem(BookItem bookItem) {
        return storage.values().stream()
                .filter(loan -> loan.getBookItem().equals(bookItem) && !loan.isReturned())
                .findFirst();
    }

    @Override
    public List<Loan> findActiveLoansByPatron(Patron patron) {
        return storage.values().stream()
                .filter(loan -> loan.getPatron().equals(patron) && !loan.isReturned())
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findAllActive() {
        return storage.values().stream()
                .filter(loan -> !loan.isReturned())
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> findByPatron(Patron patron) {
        return storage.values().stream()
                .filter(loan -> loan.getPatron().equals(patron))
                .collect(Collectors.toList());
    }
}