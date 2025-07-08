package service;

import model.Book;

public interface ShippingService {
    void ship(Book book, String address);
}