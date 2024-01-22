package org.example.Model;

import java.util.Objects;

public class Book {
    public String title;
    public String isbn_13;

    public String author;

    public String format;
    public String price;

    public Book(String title, String isbn_13, String author, String format, String price) {
        this.title = title;
        this.isbn_13 = isbn_13;
        this.author = author;
        this.format = format;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn_13() {
        return isbn_13;
    }

    public void setIsbn_13(String isbn_13) {
        this.isbn_13 = isbn_13;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(isbn_13, book.isbn_13) && Objects.equals(author, book.author) && Objects.equals(format, book.format) && Objects.equals(price, book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn_13, author, format, price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn_13='" + isbn_13 + '\'' +
                ", author='" + author + '\'' +
                ", format='" + format + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
