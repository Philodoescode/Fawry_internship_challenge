package product.concrete;

import product.Product;
import product.interfaces.Expirable;
import product.interfaces.Shippable;

import java.time.LocalDate;

public class Cheese extends Product implements Shippable, Expirable {
    private final double weight;
    private final LocalDate expiryDate;

    public Cheese(String name, double price, int quantity, double weight, LocalDate expiryDate) {
        super(name, price, quantity);
        this.weight = weight;
        this.expiryDate = expiryDate;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}