package ru.otus.gorenkov.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.gorenkov.domain.Author;
import ru.otus.gorenkov.domain.Book;
import ru.otus.gorenkov.domain.Genre;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для книг должно ")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    private BookDaoJdbc dao;

    private Book scriptBook;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @BeforeEach
    public void before() throws ParseException {

        Author author = Author.builder()
                .id(1)
                .fullName("Скриптовый автор")
                .birthYear(1900)
                .deathyear(1999)
                .build();

        scriptBook = Book.builder()
                .id(1)
                .name("Скриптовая книга")
                .author(author)
                .genre(Genre.builder().genre("фэнтези").build())
                .publicationDate(dateFormat.parse("2022/12/31"))
                .build();
    }

    @DisplayName("возвращать ожидаемую книгу")
    @Test
    void shouldReturnExpectedBook() {
        Book actualBook = dao.getById(scriptBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(scriptBook);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBookList() {
        List<Book> bookList = dao.getAll();
        assertThat(bookList).containsExactlyInAnyOrder(scriptBook);
    }

    @DisplayName("корректно обновлять книгу")
    @Test
    void shouldCorrectUpdateBook() throws ParseException {
        Book expectedBook = dao.getById(scriptBook.getId());
        Date publDate = dateFormat.parse("1995/05/02");
        expectedBook.setPublicationDate(publDate);

        dao.update(expectedBook, expectedBook.getId());
        Book resultBook = dao.getById(scriptBook.getId());

        assertThat(resultBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("удалять книгу")
    @Test
    void shouldDeleteBook() {
        dao.deleteById(scriptBook.getId());
        assertThatThrownBy(() -> dao.getById(scriptBook.getId())).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("бросать ожидаемую ошибку при инсерте дублирующей книги")
    @Test
    void shouldThrowExpectedExceptionWhenInsertDuplicateBook() {
        assertThatThrownBy(() -> dao.insert(scriptBook)).isInstanceOf(DuplicateKeyException.class);
    }

    @DisplayName("возвращать ожидаемый список книг по автору")
    @Test
    void shouldReturnExpectedBookListByAuthor() {
        List<Book> bookList = dao.findByAuthor(scriptBook.getAuthor().getFullName());
        assertThat(bookList).hasSize(1);
    }

    @DisplayName("возвращать ожидаемый список книг по жанру")
    @Test
    void shouldReturnExpectedBookListByGenre() {
        List<Book> bookList = dao.findByGenre(scriptBook.getGenre().getGenre());
        assertThat(bookList).hasSize(1);
    }
}