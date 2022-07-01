package ru.scarletarrow.diplomv1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where b.author = :author")
    List<Book> getBooksByAuthorEquals(@Param("author") Author author);
}
