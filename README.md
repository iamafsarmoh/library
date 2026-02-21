# 📚 Library Management System (Java - OOP Based)

## 📌 Overview

Library Management System ek console-based Java application hai jo librarians ko books, patrons aur lending process manage karne me help karta hai.

Is project ka focus hai:
- Object-Oriented Programming (OOP)
- SOLID Principles
- Design Patterns
- Clean Architecture
- Java Collections

⚠️ Note: Is project me database ya persistence include nahi kiya gaya. Ye in-memory system hai.

---

# 🚀 Features

## ✅ Book Management
- Add Book
- Remove Book
- Update Book
- Search Book by:
  - ISBN
  - Title
  - Author
- View All Books

## ✅ Patron Management
- Add Patron
- Update Patron Information
- Track Borrowing History

## ✅ Lending Process
- Checkout Book
- Return Book
- Track Borrowed Books

## ✅ Reservation System (Observer Pattern)
- Patron reserved book kar sakta hai
- Book available hone par notification milta hai

## ✅ Recommendation System (Strategy Pattern)
- Borrowing history ke basis par recommendation

---


# 🧠 OOP Concepts Used

### 1️⃣ Encapsulation
- Private fields
- Public getters/setters
- Business logic service layer me

### 2️⃣ Abstraction
- Interfaces:
  - RecommendationStrategy
  - Observer
  - Subject

### 3️⃣ Polymorphism
- Multiple Recommendation strategies possible
- Observer implementations interchangeable

### 4️⃣ Inheritance
- Strategy and Observer implementations

---

# 🧩 SOLID Principles Applied

| Principle | Implementation |
|-----------|---------------|
| SRP | Separate services for Book, Patron, Lending |
| OCP | New recommendation strategies add kar sakte bina existing code change kiye |
| LSP | Strategy implementations interchangeable |
| ISP | Small focused interfaces |
| DIP | Services depend on interfaces |

---

# 🎯 Design Patterns Used

## 1️⃣ Strategy Pattern
Recommendation engine ke liye use kiya gaya.

```java
public interface RecommendationStrategy {
    List<Book> recommend(Patron patron, List<Book> books);
}
```

Different recommendation algorithms easily plug kiye ja sakte hain.

---

## 2️⃣ Observer Pattern
Reservation system ke liye use kiya gaya.

Jab reserved book available hoti hai:
- Patron ko notification milta hai

---

## 3️⃣ Factory Pattern
Book object creation centralize karne ke liye use kiya gaya.

```java
Book book = BookFactory.createBook(title, author, isbn, year);
```

---

# 📊 Class Diagram (Text Representation)

```
Book
 - title
 - author
 - isbn
 - publicationYear
 - status

Patron
 - id
 - name
 - borrowingHistory

Loan
 - book
 - patron
 - issueDate

BookService
PatronService
LendingService
ReservationService
RecommendationService

RecommendationStrategy (interface)
    ↑
HistoryBasedRecommendationStrategy

Observer (interface)
    ↑
NotificationService
```

---

# 🛠️ Technologies Used

- Java 17+
- Java Collections Framework
- OOP Principles
- Design Patterns

---

# ▶️ How to Run

1. Clone the repository

```
git clone <your-repo-link>
```

2. Open project in IntelliJ / Eclipse
3. Run `Main.java`
4. Use console menu options

---

# 📌 Example Console Menu

```
1. Add Book
2. Remove Book
3. Update Book
4. Search by ISBN
5. Search by Title
6. Search by Author
7. View All Books
8. Exit
```

---

# 📈 Future Improvements

- Database integration (MySQL/PostgreSQL)
- Spring Boot REST API
- Authentication & Authorization
- GUI version
- Unit Testing (JUnit)
- Logging framework (Log4j/SLF4J)

---

# 👨‍💻 Author

Afsar Mohammad

---

# 📄 License

This project is for educational purposes.
