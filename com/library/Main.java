package com.library;

import com.library.exception.PatronNotFoundException;
import com.library.factory.BookFactory;
import com.library.model.*;
import com.library.observer.BookAvailableNotifier;
import com.library.repository.*;
import com.library.service.*;
import com.library.strategy.GenreBasedRecommendationStrategy;
import com.library.util.LoggingUtility;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static LibraryService libraryService;
    private static LendingService lendingService;
    private static ReservationService reservationService;
    private static RecommendationService recommendationService;
    private static LibraryBranch branch;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeSystem();
        scanner = new Scanner(System.in);
        showMainMenu();
    }

    private static void initializeSystem() {
        // Initialize repositories
        BookRepository bookRepo = new InMemoryBookRepository();
        PatronRepository patronRepo = new InMemoryPatronRepository();
        LoanRepository loanRepo = new InMemoryLoanRepository();

        // Create notifier (Observer pattern)
        BookAvailableNotifier notifier = new BookAvailableNotifier();

        // Initialize services
        libraryService = new LibraryService(bookRepo, patronRepo);
        lendingService = new LendingService(bookRepo, patronRepo, loanRepo);
        reservationService = new ReservationService(bookRepo, patronRepo, notifier);
        recommendationService = new RecommendationService(bookRepo,
                new GenreBasedRecommendationStrategy());

        // Create a branch
        branch = new LibraryBranch("Downtown", "123 Main St");

        // Add some sample patrons (optional)
        Patron patron1 = new Patron("Alice", "alice@example.com");
        Patron patron2 = new Patron("Bob", "bob@example.com");
        patronRepo.add(patron1);
        patronRepo.add(patron2);
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add a book");
            System.out.println("2. Remove a book");
            System.out.println("3. Update a book");
            System.out.println("4. Search books");
            System.out.println("5. Checkout a book");
            System.out.println("6. Return a book");
            System.out.println("7. Reserve a book");
            System.out.println("8. Get recommendations");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");

            int choice = readIntInput();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    searchBooks();
                    break;
                case 5:
                    checkoutBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    reserveBook();
                    break;
                case 8:
                    showRecommendations();
                    break;
                case 9:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addBook() {
        System.out.println("\n--- Add a New Book ---");
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter publication year: ");
        int year = readIntInput();

        Book book = BookFactory.createBook(isbn, title, author, year);
        libraryService.addBook(book);

        // Also add a physical copy to the branch inventory
        BookItem item = new BookItem(book, branch);
        branch.getInventory().add(item);

        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        System.out.println("\n--- Remove a Book ---");
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();

        Optional<Book> bookOpt = libraryService.findBookByIsbn(isbn);
        if (bookOpt.isPresent()) {
            libraryService.removeBook(isbn);
            // Also remove all copies from branch inventory
            branch.getInventory().removeIf(item -> item.getBook().getIsbn().equals(isbn));
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void updateBook() {
        System.out.println("\n--- Update a Book ---");
        System.out.print("Enter ISBN of the book to update: ");
        String isbn = scanner.nextLine();

        Optional<Book> bookOpt = libraryService.findBookByIsbn(isbn);
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            System.out.println("Current details: " + book);
            System.out.print("Enter new title (leave blank to keep current): ");
            String title = scanner.nextLine();
            if (!title.trim().isEmpty()) {
                book.setTitle(title);
            }
            System.out.print("Enter new author (leave blank to keep current): ");
            String author = scanner.nextLine();
            if (!author.trim().isEmpty()) {
                book.setAuthor(author);
            }
            System.out.print("Enter new publication year (0 to keep current): ");
            int year = readIntInput();
            if (year > 0) {
                book.setPublicationYear(year);
            }
            libraryService.updateBook(book);
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.println("1. Search by title");
        System.out.println("2. Search by author");
        System.out.println("3. Search by ISBN");
        System.out.print("Choose search type: ");
        int type = readIntInput();

        switch (type) {
            case 1:
                System.out.print("Enter title: ");
                String title = scanner.nextLine();
                List<Book> byTitle = libraryService.searchByTitle(title);
                printBooks(byTitle);
                break;
            case 2:
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                List<Book> byAuthor = libraryService.searchByAuthor(author);
                printBooks(byAuthor);
                break;
            case 3:
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                Optional<Book> byIsbn = libraryService.searchByIsbn(isbn);
                if (byIsbn.isPresent()) {
                    System.out.println("Found: " + byIsbn.get());
                } else {
                    System.out.println("No book found with that ISBN.");
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void checkoutBook() {
        System.out.println("\n--- Checkout a Book ---");
        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        try {
            Loan loan = lendingService.checkoutBook(isbn, patronId, branch);
            System.out.println("Book checked out successfully. Due date: " + loan.getDueDate());
        } catch (Exception e) {
            System.out.println("Checkout failed: " + e.getMessage());
            LoggingUtility.logError("Checkout error", e);
        }
    }

    private static void returnBook() {
        System.out.println("\n--- Return a Book ---");
        System.out.print("Enter book barcode: ");
        String barcode = scanner.nextLine();

        lendingService.returnBook(barcode);
        System.out.println("Book returned (if it was borrowed).");
    }

    private static void reserveBook() {
        System.out.println("\n--- Reserve a Book ---");
        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        try {
            reservationService.reserveBook(isbn, patronId);
            System.out.println("Book reserved. You'll be notified when it becomes available.");
        } catch (Exception e) {
            System.out.println("Reservation failed: " + e.getMessage());
        }
    }

    private static void showRecommendations() {
        System.out.println("\n--- Recommendations ---");
        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();

        try {
            Patron patron = libraryService.findPatronById(patronId)
                    .orElseThrow(() -> new PatronNotFoundException("Patron not found"));
            List<Book> recommendations = recommendationService.getRecommendations(patron);
            if (recommendations.isEmpty()) {
                System.out.println("No recommendations available.");
            } else {
                System.out.println("Recommended books:");
                recommendations.forEach(b -> System.out.println(" - " + b.getTitle() + " by " + b.getAuthor()));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("Found books:");
            for (Book b : books) {
                System.out.println(" - " + b);
            }
        }
    }

    private static int readIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please try again: ");
            }
        }
    }
}