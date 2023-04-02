package ru.otus.gorenkov.service;

import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Genre;

import java.util.List;

public interface BookService {

    Book save(Book book);
    Book getById(long id);
    List<Book> getAll();
//    void update(Book book, long id);
    void deleteById(long id);
    Author findAuthorByFullName(String fullName);
    Genre findGenreByGenre(String genre);
}
