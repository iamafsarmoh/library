package com.library.repository;

import com.library.model.Loan;
import com.library.model.BookItem;
import com.library.model.Patron;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void save(Loan loan);
    Optional<Loan> findActiveLoanByBookItem(BookItem bookItem);
    List<Loan> findActiveLoansByPatron(Patron patron);
    List<Loan> findAllActive();
    List<Loan> findByPatron(Patron patron);
}