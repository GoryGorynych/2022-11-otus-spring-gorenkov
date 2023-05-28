package ru.otus.gorenkov.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.gorenkov.models.Book;

public interface BookRepository extends MongoRepository<Book, String> {

    boolean existsByName(String name);
}
