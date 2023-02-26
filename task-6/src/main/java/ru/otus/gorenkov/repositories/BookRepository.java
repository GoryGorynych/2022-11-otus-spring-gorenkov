package ru.otus.gorenkov.repositories;

import ru.otus.gorenkov.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(long id);
    List<Book> findAll();
    List<Book> findByAuthor(String autorName);
    List<Book> findByGenre(String genre);
    Book save(Book book);
    void deleteById(long id);

}
