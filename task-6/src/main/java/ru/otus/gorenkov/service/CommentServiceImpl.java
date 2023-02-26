package ru.otus.gorenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.gorenkov.models.Comment;
import ru.otus.gorenkov.repositories.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;


    @Override
    @Transactional(readOnly = true)
    public Comment findById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBook(long bookId) {
        return repository.findByBookId(bookId);
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Override
    @Transactional
    public void removeOne(long commentId) {
        repository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void removeByBook(long bookId) {
        repository.deleteByBookId(bookId);
    }
}
