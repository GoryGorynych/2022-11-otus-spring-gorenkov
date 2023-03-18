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
    public Comment findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public List<CommentDto> findByBook(long bookId) {
        List<Comment> commentList = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книга не найдена")).getComments();
        return commentList.stream()
                .map(comment -> new CommentDto(comment.getNickName(), comment.getText()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void removeOne(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void removeByBook(long bookId) {
        commentRepository.deleteByBookId(bookId);
    }
}
