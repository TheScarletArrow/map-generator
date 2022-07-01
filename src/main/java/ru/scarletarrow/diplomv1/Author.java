package ru.scarletarrow.diplomv1;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
