package ru.otus.gorenkov.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.models.dto.BookWithCommentsDto;
import ru.otus.gorenkov.models.dto.CommentDto;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentRestControler {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @GetMapping("/api/v1/comments")
    public Mono<BookWithCommentsDto> showComments(@RequestParam("bookId") String bookId) {

        return commentRepository.findByBookId(bookId)
                .map(CommentDto::toDto)
                .collectList()
                .zipWith(bookRepository.getById(bookId))
                .map(objects -> {
                            List<CommentDto> comments = objects.getT1();
                            Book t2 = objects.getT2();
                            return new BookWithCommentsDto(BookDto.toDto(t2), comments);
                        }
                );
    }

    @DeleteMapping("/api/v1/comments/{id}")
    public Mono<Void> deleteComment(@PathVariable String id) {
        return commentRepository.deleteById(id);
    }

    @PostMapping("api/v1/comments")
    public Mono<CommentDto> addComment(@RequestParam String bookId, @RequestBody CommentDto commentDto) {
        return bookRepository.getById(bookId)
                .map(book -> commentDto.toDomainObject(book))
                .flatMap(commentRepository::save)
                .map(CommentDto::toDto);
    }
}
