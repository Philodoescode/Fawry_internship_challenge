package product.interfaces;

import java.time.LocalDate;


//products that can exprire
public interface Expirable {
    boolean isExpired();
    LocalDate getExpiryDate();
}