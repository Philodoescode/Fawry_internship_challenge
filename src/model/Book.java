package model;

public abstract class Book {
    protected String isbn;
    protected String title;
    protected String author;
    protected int publicationYear;
    protected double price;

    public Book(String isbn, String title, String author, int publicationYear, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.price = price;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public double getPrice() { return price; }

    public abstract double buy(int quantity, String... customerInfo) throws Exception;
}