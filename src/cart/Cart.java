package cart;

import product.Product;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    // a map to hold the product and the desired quantity o(1)
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (quantity > product.getQuantity()) {
            throw new IllegalStateException("Cannot add to cart. Requested quantity for " + product.getName() + " is not available.");
        }

        this.items.put(product, quantity);
        System.out.println("Added " + quantity + "x " + product.getName() + " to cart.");
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void clear() {
        this.items.clear();
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}