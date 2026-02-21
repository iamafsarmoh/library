package com.library.factory;

import com.library.model.Book;

public class BookFactory {
    public static Book createBook(String isbn, String title, String author, int year) {
        // Could add validation or default values
        return new Book(isbn, title, author, year);
    }
}