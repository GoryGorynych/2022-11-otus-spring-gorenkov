package ru.otus.gorenkov.repositories;

import ru.otus.gorenkov.models.Author;

public interface AuthorRepository {

    Author findByFullName(String fullName);
}
