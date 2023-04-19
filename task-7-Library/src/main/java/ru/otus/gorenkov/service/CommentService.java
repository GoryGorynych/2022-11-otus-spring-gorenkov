package ru.otus.gorenkov.service;

import ru.otus.gorenkov.models.Comment;
import ru.otus.gorenkov.models.dto.CommentDto;

import java.util.List;

public interface CommentService {

    Comment findById(long id);
    List<CommentDto> findByBook(long bookId);
    Comment save(Comment comment);
    void removeOne(long commentId);
    void removeByBook(long bookId);

}
