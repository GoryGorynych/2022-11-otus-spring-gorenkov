package ru.otus.gorenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.gorenkov.models.Comment;
import ru.otus.gorenkov.models.dto.CommentDto;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;


    @Override
    public Comment findById(String id) {
        return commentRepository.findById(id).orElse(new Comment());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByBook(String bookId) {
        List<Comment> commentList = commentRepository.findByBookId(bookId);
        return commentList.stream()
                .map(comment -> new CommentDto(comment.getId(), comment.getNickName(), comment.getText()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void removeOne(String commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void removeByBook(String bookId) {
        commentRepository.deleteByBookId(bookId);
    }
}
