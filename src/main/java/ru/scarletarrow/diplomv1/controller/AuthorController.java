package ru.scarletarrow.diplomv1.controller;


import org.hibernate.annotations.BatchSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import ru.scarletarrow.diplomv1.Author;
import ru.scarletarrow.diplomv1.AuthorRepository;
import ru.scarletarrow.diplomv1.Book;
import ru.scarletarrow.diplomv1.BookRepository;
import ru.scarletarrow.diplomv1.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AuthorController {
    @Autowired
    private  AuthorRepository authorRepository;
    @Autowired
    private  BookRepository bookRepository;


    @QueryMapping
    @BatchSize(size = 2)
    Iterable<Author> authors() {
        return authorRepository.findAll();
    }

    @BatchMapping
    @BatchSize(size = 2)
    Map<Author, List<Book>> author(List<Author> authors) {
        return authors.stream()
                .collect(Collectors.toMap(author -> author,
                        author -> bookRepository.getBooksByAuthorEquals(author)
                ));
    }

    @QueryMapping
    Optional<Author> authorById(@Argument Long id) {
        return authorRepository.findById(id);
    }

    @MutationMapping
    Book addBook(@Argument BookInput book) {
        Author author = authorRepository.findById(book.authorId()).orElseThrow(UserNotFoundException::new);
        Book b = new Book(book.title(), book.publisher(), author);
        return bookRepository.save(b);
    }

    record BookInput(String title, String publisher, Long authorId) {
    }
}
