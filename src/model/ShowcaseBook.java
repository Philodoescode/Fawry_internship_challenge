package model;

import exception.BookNotForSaleException;

public class ShowcaseBook extends Book {

    public ShowcaseBook(String isbn, String title, String author, int publicationYear) {
        super(isbn, title, author, publicationYear, 0.0);
    }

    @Override
    public double buy(int quantity, String... customerInfo) throws Exception {
        throw new BookNotForSaleException("The book '" + this.title + "' is a showcase item and not for sale.");
    }
}