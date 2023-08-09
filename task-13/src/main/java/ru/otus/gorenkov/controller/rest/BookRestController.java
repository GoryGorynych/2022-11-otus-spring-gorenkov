package ru.otus.gorenkov.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/v1/books")
    public List<BookDto> listBooks() {
        List<BookDto> books = bookService.getAll().stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());
        return books;
    }

    @GetMapping("/api/v1/books/{id}")
    public BookDto getOneBook(@PathVariable String id) {
        return BookDto.toDto(bookService.getById(id));
    }

    @PutMapping("/api/v1/books/{id}")
    public BookDto updateBook(@PathVariable String id, @RequestBody BookDto bookDto) {
        bookDto.setId(id);
        return BookDto.toDto(bookService.update(bookDto));
    }

    @PostMapping("/api/v1/books")
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return BookDto.toDto(bookService.save(bookDto.toDomainObject()));
    }

    @DeleteMapping("/api/v1/books/{id}")
    public void deleteBook(@PathVariable String id) {
        bookService.deleteByIdWithComments(id);
    }
}
