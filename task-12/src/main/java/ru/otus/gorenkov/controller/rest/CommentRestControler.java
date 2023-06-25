package ru.otus.gorenkov.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.models.dto.BookWithCommentsDto;
import ru.otus.gorenkov.models.dto.CommentDto;
import ru.otus.gorenkov.service.BookService;
import ru.otus.gorenkov.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentRestControler {

    private final CommentService commentService;
    private final BookService bookService;

    @GetMapping("/api/v1/comments")
    public BookWithCommentsDto showComments(@RequestParam("bookId") String bookId) {
        List<CommentDto> comments = commentService.findByBook(bookId);
        BookDto bookDto = BookDto.toDto(bookService.getById(bookId));
        BookWithCommentsDto bookWithCommentsDto = new BookWithCommentsDto(bookDto, comments);
        return bookWithCommentsDto;
    }

    @DeleteMapping("/api/v1/comments/{id}")
    public void deleteComment(@PathVariable String id) {
        commentService.removeOne(id);
    }

    @PostMapping("api/v1/comments")
    public CommentDto addComment(@RequestParam String bookId, @RequestBody CommentDto commentDto) {
        Book book = bookService.getById(bookId);
        return commentService.save(commentDto.toDomainObject(book));
    }
}
