import cart.Cart;
import customer.Customer;
import product.Product;
import product.concrete.*;
import service.CheckoutService;
import service.ShippingService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        ShippingService shippingService = new ShippingService();
        CheckoutService checkoutService = new CheckoutService(shippingService);

        Product cheese = new Cheese("Cheese", 100.0, 10, 0.4, LocalDate.now().plusMonths(3));
        Product biscuits = new Biscuits("Biscuits", 50.0, 20, 0.7, LocalDate.now().plusYears(1));
        Product tv = new TV("Samsung TV", 5000.0, 5, 15.0);
        Product scratchCard = new MobileScratchCard("Vodafone Card", 25.0, 100);
        Product expiredCheese = new Cheese("Expired Cheese", 90.0, 5, 0.4, LocalDate.now().minusDays(1));


        // first scenario : successful checkout
        System.out.println("1 Successful Checkout");
        Customer daniel = new Customer("Daniel", 10000.0);
        Cart danielsCart = new Cart();

        danielsCart.addProduct(cheese, 2); // Shippable, Expirable
        danielsCart.addProduct(biscuits, 1); // Shippable, Expirable
        danielsCart.addProduct(tv, 1); // Shippable
        danielsCart.addProduct(scratchCard, 3); // Not shippable, not expirable

        checkoutService.checkout(daniel, danielsCart);

        //second scenario : insufficient balance
        System.out.println("2 Insufficient Balance");
        Customer aliaa = new Customer("Aliaa", 100.0);
        Cart aliaasCart = new Cart();

        aliaasCart.addProduct(tv, 1); // tv costs 5k and Bob only has 100

        checkoutService.checkout(aliaa, aliaasCart);


        // third scenario : out of stock
        System.out.println("3 Out of Stock");
        Customer hamada = new Customer("hamada", 20000.0);
        Cart hamadasCart = new Cart();

        System.out.println("Initial TV stock: " + tv.getQuantity());
        //only 4 tvs left after Alice bought one. hamada wants 5.
        try {
            hamadasCart.addProduct(tv, 5);
        } catch (IllegalStateException e) {
            System.out.println("ERROR: " + e.getMessage());
        }


        // forth scenario : expired product
        System.out.println("4 Expired Product");
        Customer ahmed = new Customer("Ahmed", 500.0);
        Cart ahmedsCart = new Cart();

        ahmedsCart.addProduct(expiredCheese, 1);

        checkoutService.checkout(ahmed, ahmedsCart);


        // fifth and last scenario : empty cart
        System.out.println("5 empty cart");
        Customer dummy = new Customer("Dumb", 1000.0);
        Cart dummysCart = new Cart();

        checkoutService.checkout(dummy, dummysCart);
    }
}