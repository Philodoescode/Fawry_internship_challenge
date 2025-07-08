package service;

import model.Book;

public interface MailService {
    void send(Book book, String email);
}