package ru.otus.gorenkov.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.gorenkov.models.Book;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    boolean existsByName(String name);
    Optional<Book> getByName(String name);
    Optional<Book> getById(String id);
}
