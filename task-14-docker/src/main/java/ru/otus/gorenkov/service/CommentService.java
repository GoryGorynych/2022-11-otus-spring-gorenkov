package ru.otus.gorenkov.service;

import ru.otus.gorenkov.models.Comment;
import ru.otus.gorenkov.models.dto.CommentDto;

import java.util.List;

public interface CommentService {

    Comment findById(String id);
    List<CommentDto> findByBook(String bookId);
    CommentDto save(Comment comment);
    void removeOne(String commentId);
    void removeByBook(String bookId);

}
