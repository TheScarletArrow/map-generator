package ru.scarletarrow.diplomv1;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private  Long id;

    private String title;

    private String publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    public Book(Long id) {
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public Author getAuthor() {
        return author;
    }

    public Book(String title, String publisher, Author author) {
        this.title = title;
        this.publisher = publisher;
        this.author = author;
    }

    public Book() {
    }
}
