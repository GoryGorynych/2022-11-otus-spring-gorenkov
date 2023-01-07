package ru.otus.gorenkov.dao;

import ru.otus.gorenkov.domain.Genre;

import java.util.List;

public interface GenreDao {

    void insert(Genre genre);
    Genre get(String genre);
    List<Genre> getAll();
    void update(Genre genre);
    void delete(String genre);
    boolean isExists(String genre);
}
