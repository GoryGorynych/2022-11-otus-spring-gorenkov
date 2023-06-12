package ru.otus.gorenkov.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.gorenkov.models.Book;

import java.util.Optional;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Mono<Boolean> existsByName(String name);
    Mono<Book> getByName(String name);
    Mono<Book> getById(String id);
}
