import model.*;
import service.MailService;
import service.ShippingService;

public class QuantumBookstoreFullTest {

    // Helper for standardized logging output, specific to the test harness
    private static void logTest(String message) {
        System.out.println("\n----- " + message + " -----");
    }

    public static void main(String[] args) {
        // 1. Setup mock services. In a real app, these would be actual implementations.
        // This is a form of Dependency Injection, which is a great pattern.
        ShippingService mockShippingService = (book, address) ->
                System.out.println("[Shipping Service] Initiating shipment for '" + book.getTitle() + "' to " + address);

        MailService mockMailService = (book, email) ->
                System.out.println("[Mail Service] Sending '" + book.getTitle() + "' to " + email);

        // 2. Initialize the bookstore
        Bookstore store = new Bookstore();

        // 3. Add books to the inventory
        logTest("Adding Books to Inventory");
        store.addBook(new PaperBook("978-0321765723", "Book1", "me", 2013, 60.50, 10, mockShippingService));
        store.addBook(new PaperBook("978-0132350884", "Book2", "me1", 2008, 45.25, 5, mockShippingService));
        store.addBook(new EBook("978-0134494166", "Book3", "me2", 2018, 35.00, "PDF", mockMailService));
        store.addBook(new ShowcaseBook("000-0000000000", "Book4", "me3", 1985));
        store.addBook(new PaperBook("978-0201616224", "Book5", "me3", 1995, 30.00, 3, mockShippingService));

        store.printInventory();

        // 4. Test buying books
        logTest("Testing Purchase Scenarios");

        // --- Success Case: Buy a PaperBook ---
        try {
            store.buyBook("978-0321765723", 2, "student@university.edu", "123 College Ave, Booktown, USA");
        } catch (Exception e) {
            System.err.println("Purchase failed: " + e.getMessage());
        }

        // --- Success Case: Buy an EBook ---
        try {
            store.buyBook("978-0134494166", 1, "another.student@university.edu", "N/A");
        } catch (Exception e) {
            System.err.println("Purchase failed: " + e.getMessage());
        }

        // --- Failure Case: Insufficient Stock ---
        try {
            store.buyBook("978-0132350884", 10, "ambitious.student@university.edu", "456 Dormitory Ln");
        } catch (Exception e) {
            System.err.println("Purchase failed as expected: " + e.getMessage());
        }

        // --- Failure Case: Book Not for Sale ---
        try {
            store.buyBook("000-0000000000", 1, "curious.student@university.edu", "N/A");
        } catch (Exception e) {
            System.err.println("Purchase failed as expected: " + e.getMessage());
        }

        // --- Failure Case: Book Not Found ---
        try {
            store.buyBook("999-9999999999", 1, "lost.student@university.edu", "N/A");
        } catch (Exception e) {
            System.err.println("Purchase failed as expected: " + e.getMessage());
        }

        logTest("Inventory Status After Purchases");
        store.printInventory();

        // 5. Test removing outdated books
        logTest("Removing Books Older Than 25 Years");
        store.removeOutdatedBooks(25);

        logTest("Final Inventory Status");
        store.printInventory();
    }
}