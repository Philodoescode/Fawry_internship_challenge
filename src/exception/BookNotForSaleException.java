package exception;

public class BookNotForSaleException extends Exception {
    public BookNotForSaleException(String message) {
        super(message);
    }
}