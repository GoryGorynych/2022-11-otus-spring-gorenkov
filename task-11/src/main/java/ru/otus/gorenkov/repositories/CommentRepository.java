package ru.otus.gorenkov.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.gorenkov.models.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    Mono<Void> deleteByBookId(String bookId);
    Mono<Void> deleteById(String id);
    Flux<Comment> findByBookId (String bookId);

}
