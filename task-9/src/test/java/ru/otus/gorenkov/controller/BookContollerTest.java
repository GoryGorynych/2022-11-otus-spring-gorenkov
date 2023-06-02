package ru.otus.gorenkov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Genre;
import ru.otus.gorenkov.repositories.BookRepository;
import ru.otus.gorenkov.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookContoller.class)
class BookContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void listBooks() throws Exception {
        List<Book> books = List.of(getBookN(1), getBookN(2));

        given(bookService.getAll()).willReturn(books);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("books"))
                .andExpect(result -> {
                    String view = result.getModelAndView().getViewName();
                    Assertions.assertThat(view).isEqualTo("list");
                });
    }

    @Test
    void editBookPage() throws Exception {
        Book book = getBookN(1);

        given(bookService.getById(anyString())).willReturn(book);

        mockMvc.perform(
                get("/edit").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("book"))
                .andExpect(result -> {
                    Book actualBook = (Book) result.getModelAndView().getModel().get("book");
                    Assertions.assertThat(actualBook).isEqualTo(book);
                });
    }

    @Test
    void saveBook() throws Exception {
        mockMvc.perform(
                post("/edit"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void deleteBook() throws Exception {

        mockMvc.perform(
                        post("/delete").param("id", "id1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }


    public Book getBookN(int n) {
        return Book.builder()
                .name("Book " + n)
                .author(new Author("Author " + n))
                .genre(new Genre("Genre " + n))
                .build();
    }

}