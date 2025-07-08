import exception.BookNotFoundException;
import model.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Bookstore {
    // Using a ConcurrentHashMap is a good practice for thread-safety in a real-world scenario.
    private final Map<String, Book> inventory = new ConcurrentHashMap<>();

    // Helper for standardized logging output
    private void log(String message) {
        System.out.println(message);
    }

    public void addBook(Book book) {
        inventory.put(book.getIsbn(), book);
        log("Added to inventory: " + book.getTitle());
    }

    public double buyBook(String isbn, int quantity, String email, String address) throws Exception {
        Book book = inventory.get(isbn);
        if (book == null) {
            throw new BookNotFoundException("No book found with ISBN: " + isbn);
        }

        // Polymorphism at work! We call buy() on the Book object.
        // The object itself knows how to handle the purchase based on its type (Paper, EBook, etc.).
        // The Bookstore class doesn't need to know the specific type.
        double totalPaid = book.buy(quantity, email, address);
        log("Successfully purchased " + book.getTitle() + ". Amount paid: $" + String.format("%.2f", totalPaid));
        return totalPaid;
    }

    public List<Book> removeOutdatedBooks(int yearsOld) {
        int currentYear = LocalDate.now().getYear();

        List<Book> removedBooks = inventory.values().stream()
                .filter(book -> (currentYear - book.getPublicationYear()) > yearsOld)
                .collect(Collectors.toList());

        if (removedBooks.isEmpty()) {
            log("No books found older than " + yearsOld + " years.");
        } else {
            removedBooks.forEach(book -> {
                inventory.remove(book.getIsbn());
                log("Removed outdated book: " + book.getTitle() + " (Published: " + book.getPublicationYear() + ")");
            });
        }
        return removedBooks;
    }

    public void printInventory() {
        log("Current Inventory Status:");
        if(inventory.isEmpty()) {
            System.out.println("  Inventory is empty.");
            return;
        }
        inventory.values().forEach(book -> System.out.println("  - " + book.toString()));
    }
}