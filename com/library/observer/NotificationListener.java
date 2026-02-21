package com.library.observer;

import com.library.model.Book;

public interface NotificationListener {
    void onBookAvailable(Book book);
}