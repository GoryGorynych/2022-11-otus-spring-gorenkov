package ru.otus.gorenkov.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.repositories.BookRepository;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

//@Configuration
@RequiredArgsConstructor
public class BookEndPointConfig {

    private final BookRepository bookRepository;

//    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route()
                .GET("/api/v1/books", this::listBooks)
                .GET("/api/v1/books/{id}", accept(MediaType.APPLICATION_JSON), this::getOneBook)
                .build();
    }

    private Mono<ServerResponse> listBooks(ServerRequest request) {
        Flux<BookDto> books = bookRepository.findAll()
                .map(BookDto::toDto);
        return ok().body(books, BookDto.class);
    }

    private Mono<ServerResponse> getOneBook(ServerRequest request) {
        Mono<BookDto> book = bookRepository.getById(request.pathVariable("id"))
                .map(BookDto::toDto);
        return ok().body(book, BookDto.class);
    }
}
