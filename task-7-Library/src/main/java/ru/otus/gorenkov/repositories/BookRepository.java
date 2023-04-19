package ru.otus.gorenkov.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);

    @Override
    @EntityGraph(attributePaths = {"genre", "author"})
    List<Book> findAll();

}
