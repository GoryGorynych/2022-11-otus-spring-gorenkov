package ru.otus.gorenkov.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Genre;
import ru.otus.gorenkov.models.dto.BookDto;
import ru.otus.gorenkov.security.SecurityConfiguration;
import ru.otus.gorenkov.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
@Import(SecurityConfiguration.class)
class BookRestControllerTest {

    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @WithMockUser(username = "admin")
    @Test
    void listBooks() throws Exception {
        List<Book> books = List.of(getBookN(1), getBookN(2));

        List<BookDto> bookDtos = books.stream()
                .map(BookDto::toDto)
                .collect(Collectors.toList());

        given(bookService.getAll()).willReturn(books);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(bookDtos)));
    }

    @WithMockUser(username = "admin")
    @Test
    void getOneBook() throws Exception {
        Book book = getBookN(1);
        given(bookService.getById(anyString())).willReturn(book);

        BookDto bookDto = BookDto.toDto(book);

        mockMvc.perform(get("/api/v1/books/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(bookDto)));
    }

    @WithMockUser(username = "admin")
    @Test
    void updateBook() throws Exception {

        Book book = getBookN(1);
        book.setName("New name");
        given(bookService.update(any())).willReturn(book);

        BookDto updateBookDto = BookDto.toDto(book);

        mockMvc.perform(put("/api/v1/books/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBookDto))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(updateBookDto)));
    }

    @WithMockUser(username = "admin")
    @Test
    void addBook() throws Exception {

        Book book = getBookN(1);
        given(bookService.save(any())).willReturn(book);

        BookDto bookDto = BookDto.toDto(book);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(bookDto)));
    }

    @WithMockUser(username = "admin")
    @Test
    void deleteBook() throws Exception {

        mockMvc.perform(delete("/api/v1/books/{id}", "1"))
                .andExpect(status().isOk())
                .andReturn();
    }

    public Book getBookN(int n) {
        return Book.builder()
                .name("Book " + n)
                .author(new Author("Author " + n))
                .genre(new Genre("Genre " + n))
                .build();
    }
}