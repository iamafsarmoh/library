package com.library.service;

import com.library.model.Book;
import com.library.model.Patron;
import com.library.repository.BookRepository;
import com.library.strategy.RecommendationStrategy;
import com.library.util.LoggingUtility;

import java.util.List;

public class RecommendationService {
    private final BookRepository bookRepository;
    private RecommendationStrategy strategy;

    public RecommendationService(BookRepository bookRepository, RecommendationStrategy strategy) {
        this.bookRepository = bookRepository;
        this.strategy = strategy;
    }

    public void setStrategy(RecommendationStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Book> getRecommendations(Patron patron) {
        List<Book> allBooks = bookRepository.findAll();
        List<Book> recommendations = strategy.recommend(patron, allBooks);
        LoggingUtility.logInfo("Generated recommendations for patron: " + patron.getId());
        return recommendations;
    }
}