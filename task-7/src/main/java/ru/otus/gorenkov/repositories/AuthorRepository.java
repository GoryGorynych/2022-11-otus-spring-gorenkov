package ru.otus.gorenkov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.gorenkov.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByFullName(String fullName);
}
