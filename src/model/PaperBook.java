package model;

import exception.InsufficientStockException;
import service.ShippingService;

public class PaperBook extends Book {
    private int stock;
    private final ShippingService shippingService;

    public PaperBook(String isbn, String title, String author, int publicationYear, double price, int stock, ShippingService shippingService) {
        super(isbn, title, author, publicationYear, price);
        this.stock = stock;
        this.shippingService = shippingService;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public double buy(int quantity, String... customerInfo) throws Exception {
        if (quantity > this.stock) {
            throw new InsufficientStockException("Not enough stock for '" + this.title + "'. Available: " + this.stock + ", Requested: " + quantity);
        }
        this.stock -= quantity;

        String address = customerInfo.length > 1 ? customerInfo[1] : "N/A";
        shippingService.ship(this, address);

        return this.price * quantity;
    }
}