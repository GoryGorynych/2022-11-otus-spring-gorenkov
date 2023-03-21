package ru.otus.gorenkov.repositories;

import ru.otus.gorenkov.models.Genre;

public interface GenreRepository {

    Genre getGenreByGenre(String genre);
}
