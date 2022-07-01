package ru.scarletarrow.diplomv1;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Override
    @EntityGraph(attributePaths = {"books"})
    List<Author> findAll();
}
