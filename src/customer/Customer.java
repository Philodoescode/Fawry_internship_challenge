package customer;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void debit(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Debit amount cannot be greater than balance.");
        }
        this.balance -= amount;
    }
}