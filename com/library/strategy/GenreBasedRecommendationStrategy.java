package com.library.strategy;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Patron;
import java.util.*;
import java.util.stream.Collectors;

public class GenreBasedRecommendationStrategy implements RecommendationStrategy {
    @Override
    public List<Book> recommend(Patron patron, List<Book> allBooks) {
        // Find genres patron has borrowed (simplified: use author as genre proxy)
        Set<String> borrowedAuthors = patron.getBorrowingHistory().stream()
                .map(loan -> loan.getBookItem().getBook().getAuthor())
                .collect(Collectors.toSet());

        return allBooks.stream()
                .filter(book -> borrowedAuthors.contains(book.getAuthor()))
                .limit(5)
                .collect(Collectors.toList());
    }
}