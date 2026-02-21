package com.library.observer;

import com.library.model.Book;
import com.library.model.Patron;
import com.library.util.LoggingUtility;

public class PatronNotifier implements NotificationListener {
    private final Patron patron;

    public PatronNotifier(Patron patron) {
        this.patron = patron;
    }

    @Override
    public void onBookAvailable(Book book) {
        // In real system, send email/SMS. Here we just log.
        LoggingUtility.logInfo("Notifying patron " + patron.getName() + " that book '" + book.getTitle() + "' is available.");
    }
}