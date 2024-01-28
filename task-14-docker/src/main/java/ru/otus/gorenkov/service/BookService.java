package ru.otus.gorenkov.service;

import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.dto.BookDto;

import java.util.List;

public interface BookService {

    Book save(Book book);
    Book getById(String id);
    List<Book> getAll();
    void deleteById(String id);

    void deleteByIdWithComments(String id);
    Book getByName(String name);
    Book update(BookDto bookDto);
}
