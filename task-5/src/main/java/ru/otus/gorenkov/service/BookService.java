package ru.otus.gorenkov.service;

import ru.otus.gorenkov.domain.Book;

import java.util.List;

public interface BookService {

    long save(Book book);
    Book getById(long id);
    List<Book> getAll();
    void update(Book book, long id);
    void deleteById(long id);

}
