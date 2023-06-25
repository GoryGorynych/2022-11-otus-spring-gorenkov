package ru.otus.gorenkov.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.gorenkov.models.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    void deleteByBookId(String bookId);
    void deleteById(String id);
    List<Comment> findByBookId (String bookId);

}
