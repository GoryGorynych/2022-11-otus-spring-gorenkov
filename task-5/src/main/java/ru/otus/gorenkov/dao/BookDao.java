package ru.otus.gorenkov.dao;

import ru.otus.gorenkov.domain.Book;

import java.util.List;

public interface BookDao {

    long insert(Book book);
    Book getById(long id);
    List<Book> getAll();
    void update(Book book, long id);
    void deleteById(long id);
    List<Book> findByAuthor(String authorName);
    List<Book> findByGenre(String genre);
}
