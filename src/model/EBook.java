package model;

import service.MailService;

public class EBook extends Book {
    private String fileType;
    private final MailService mailService;

    public EBook(String isbn, String title, String author, int publicationYear, double price, String fileType, MailService mailService) {
        super(isbn, title, author, publicationYear, price);
        this.fileType = fileType;
        this.mailService = mailService;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public double buy(int quantity, String... customerInfo) {
        // quantity always 1
        String email = customerInfo.length > 0 ? customerInfo[0] : "N/A";
        mailService.send(this, email);

        return this.price;
    }
}