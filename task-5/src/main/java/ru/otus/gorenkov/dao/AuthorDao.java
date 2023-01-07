package ru.otus.gorenkov.dao;

import ru.otus.gorenkov.domain.Author;

import java.util.List;

public interface AuthorDao {

    long insert(Author author);
    Author getById(long id);
    List<Author> getAll();
    void update(Author author, long id);
    void deleteById(long id);
    boolean isExists(String fullName);
}
