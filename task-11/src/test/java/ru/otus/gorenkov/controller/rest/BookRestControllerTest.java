package ru.otus.gorenkov.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Genre;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.repositories.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@WebFluxTest
class BookRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private CommentRepository commentRepository;

    @Test
    void listBooks() {

        List<Book> books = List.of(getBookN(1), getBookN(2));

        Flux<Book> bookFlux = Flux.fromIterable(books);

        List<BookDto> expectedBookDtos = books.stream().map(BookDto::toDto).collect(Collectors.toList());

        given(bookRepository.findAll()).willReturn(bookFlux);

        webTestClient.get()
                .uri("/api/v1/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BookDto.class)
                .isEqualTo(expectedBookDtos);
    }

    @Test
    void getOneBook() {

        Book book = getBookN(1);

        Mono<Book> bookMono = Mono.just(book);

        BookDto expectedBookDto = BookDto.toDto(book);

        given(bookRepository.getById(anyString())).willReturn(bookMono);

        webTestClient.get()
                .uri("/api/v1/books/{id}", "1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedBookDto);
    }

    @Test
    void updateBook() {

        Book book = getBookN(1);

        Mono<Book> bookMono = Mono.just(book);

        BookDto expectedBookDto = BookDto.toDto(book);

        given(bookRepository.getById(anyString())).willReturn(bookMono);
        given(bookRepository.save(any())).willReturn(bookMono);

        webTestClient.put()
                .uri("/api/v1/books/{id}", "1")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(expectedBookDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedBookDto);
    }

    @Test
    void addBook() {
        Book book = getBookN(1);

        Mono<Book> bookMono = Mono.just(book);

        BookDto expectedBookDto = BookDto.toDto(book);

        given(bookRepository.existsByName(anyString())).willReturn(Mono.just(Boolean.FALSE));
        given(bookRepository.save(any())).willReturn(bookMono);

        webTestClient.post()
                .uri("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(expectedBookDto))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(BookDto.class)
                .isEqualTo(expectedBookDto);
    }

    @Test
    void deleteBook() {

        given(bookRepository.deleteById(anyString())).willReturn(Mono.empty());
        given(commentRepository.deleteByBookId(anyString())).willReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/books/{id}", "1")
                .exchange()
                .expectStatus()
                .isOk();

    }

    public Book getBookN(int n) {
        return Book.builder()
                .name("Book " + n)
                .author(new Author("Author " + n))
                .genre(new Genre("Genre " + n))
                .build();
    }
}