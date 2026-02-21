package com.library.observer;

import com.library.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BookAvailableNotifier {
    private final List<NotificationListener> listeners = new ArrayList<>();

    public void subscribe(NotificationListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(NotificationListener listener) {
        listeners.remove(listener);
    }

    public void notifyBookAvailable(Book book) {
        for (NotificationListener listener : listeners) {
            listener.onBookAvailable(book);
        }
    }
}