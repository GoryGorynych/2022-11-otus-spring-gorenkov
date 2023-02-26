package ru.otus.gorenkov.repositories;

import ru.otus.gorenkov.models.Comment;

import java.util.List;

public interface CommentRepository {

    Comment findById(long id);
    List<Comment> findByBookId(long bookId);
    Comment save(Comment comment);
    void deleteById(long id);
    void deleteByBookId(long bookId);
}
