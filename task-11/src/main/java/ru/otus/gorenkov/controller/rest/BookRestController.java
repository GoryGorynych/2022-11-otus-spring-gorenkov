package ru.otus.gorenkov.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.gorenkov.exception.DuplicateBookException;
import ru.otus.gorenkov.exception.NotFoundBookException;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.CommentRepository;

@RequiredArgsConstructor
@RestController
public class BookRestController {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @GetMapping(value = "/api/v1/books")
    public Flux<BookDto> listBooks() {
        Flux<BookDto> books = bookRepository.findAll()
                .map(BookDto::toDto);
        return books;
    }

    @GetMapping("/api/v1/books/{id}")
    public Mono<BookDto> getOneBook(@PathVariable String id) {
        return bookRepository.getById(id).map(BookDto::toDto);
    }

    @PutMapping("/api/v1/books/{id}")
    public Mono<BookDto> updateBook(@PathVariable String id, @RequestBody BookDto bookDto) {

        return bookRepository.getById(id)
                .switchIfEmpty(Mono.error(new NotFoundBookException("Book not found by Id")))
                .map(book -> {
                    book.setName(bookDto.getName());
                    book.setAuthor(bookDto.getAuthor().toDomainObject());
                    book.setGenre(bookDto.getGenre().toDomainObject());
                    return book;
                })
                .flatMap(bookRepository::save)
                .map(BookDto::toDto);

//        return bookRepository.getById(id)
//                .zipWith(Mono.just(bookDto))
//                .map(objects -> {
//                    Book book = objects.getT1();
//                    BookDto bookDto1 = objects.getT2();
//
//                    book.setName(bookDto1.getName());
//                    book.setAuthor(bookDto1.getAuthor().toDomainObject());
//                    book.setGenre(bookDto1.getGenre().toDomainObject());
//                    return book;
//                })
//                .flatMap(bookRepository::save)
//                .map(BookDto::toDto);
    }

    @PostMapping("/api/v1/books")
    public Mono<BookDto> addBook(@RequestBody BookDto bookDto) {

        return bookRepository.existsByName(bookDto.getName())
                .filter(exists -> !exists)
                .switchIfEmpty(Mono.error(new DuplicateBookException("Book already exists")))
                .then(Mono.just(bookDto.toDomainObject()))
                .flatMap(bookRepository::save)
                .map(BookDto::toDto);

//        return bookRepository.getByName(bookDto.getName())
//                .switchIfEmpty(Mono.just(bookDto.toDomainObject()))
//                .flatMap(bookRepository::save)
//                .map(BookDto::toDto);


    }

    @DeleteMapping("/api/v1/books/{id}")
    public Flux<Void> deleteBook(@PathVariable String id) {
        return Flux.concat(commentRepository.deleteByBookId(id),
                bookRepository.deleteById(id));
    }
}
