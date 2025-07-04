package service;

import cart.Cart;
import customer.Customer;
import product.Product;
import product.interfaces.Expirable;
import product.interfaces.Shippable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckoutService {
    private final ShippingService shippingService;
    private static final double SHIPPING_FEE_PER_ITEM = 15.0; // simple assumption

    public CheckoutService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public void checkout(Customer customer, Cart cart) {
        try {
            // validation
            validateCart(cart);
            validateProducts(cart);

            // calculation
            double subtotal = 0;
            List<Shippable> shippableItems = new ArrayList<>();
            StringBuilder receiptBuilder = new StringBuilder();

            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                double itemTotal = product.getPrice() * quantity;
                subtotal += itemTotal;

                receiptBuilder.append(String.format("%dx %-18s %.2f%n", quantity, product.getName(), itemTotal));

                if (product instanceof Shippable) {
                    for (int i = 0; i < quantity; i++) {
                        shippableItems.add((Shippable) product);
                    }
                }
            }

            double shippingFees = shippableItems.size() * SHIPPING_FEE_PER_ITEM;
            double totalAmount = subtotal + shippingFees;
            validateCustomerBalance(customer, totalAmount);

            customer.debit(totalAmount);
            updateProductStock(cart);

            shippingService.processShipment(shippableItems);

            System.out.println("\n Checkout receipt");
            System.out.print(receiptBuilder);
            System.out.println("---------------------------");
            System.out.printf("Subtotal: %.2f%n", subtotal);
            System.out.printf("Shipping: %.2f%n", shippingFees);
            System.out.printf("Paid Amount: %.2f%n", totalAmount);
            System.out.printf("Your new balance: %.2f%n", customer.getBalance());

            cart.clear();

        } catch (IllegalStateException e) {
            System.out.println("ERROR: Checkout failed. " + e.getMessage());
        }
    }

    private void validateCart(Cart cart) {
        if (cart.isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }
    }

    // check if product is out of stock or expired
    private void validateProducts(Cart cart) {
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int requestedQuantity = entry.getValue();

            if (requestedQuantity > product.getQuantity()) {
                throw new IllegalStateException(product.getName()); // probably useless as it will it not be added to the cart at the first place
            }

            if (product instanceof Expirable && ((Expirable) product).isExpired()) {
                throw new IllegalStateException(product.getName());
            }
        }
    }

    private void validateCustomerBalance(Customer customer, double totalAmount) {
        if (customer.getBalance() < totalAmount) {
            throw new IllegalStateException("Insufficient balance.");
        }
    }

    private void updateProductStock(Cart cart) {
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            Product product = entry.getKey();
            int quantityInCart = entry.getValue();
            product.setQuantity(product.getQuantity() - quantityInCart);
        }
    }
}