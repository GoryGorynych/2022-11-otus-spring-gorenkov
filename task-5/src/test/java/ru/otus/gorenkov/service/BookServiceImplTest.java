package ru.otus.gorenkov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.gorenkov.dao.AuthorDaoJdbc;
import ru.otus.gorenkov.dao.BookDaoJdbc;
import ru.otus.gorenkov.dao.GenreDaoJdbc;
import ru.otus.gorenkov.domain.Author;
import ru.otus.gorenkov.domain.Book;
import ru.otus.gorenkov.domain.Genre;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для книг должен ")
@JdbcTest
@Import({BookServiceImpl.class, BookDaoJdbc.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;
    @MockBean
    private BookDaoJdbc bookDao;
    @MockBean
    private GenreDaoJdbc genreDao;
    @MockBean
    private AuthorDaoJdbc authorDao;

    private Author testedAuthor;
    private Book testedBook;
    private Genre testedGenre;

    @BeforeEach
    public void before() {
        testedAuthor = Author.builder()
                .fullName("Новый автор")
                .birthYear(1900)
                .deathyear(1999)
                .birthcountry("Неизвестная страна")
                .build();

        testedGenre = Genre.builder()
                .genre("Неизвестный жанр")
                .build();

        testedBook = Book.builder()
                .name("Новая книга")
                .author(testedAuthor)
                .genre(testedGenre)
                .build();
    }

    @DisplayName("корректно сохранять книгу если автор и жанр не существует")
    @Test
    void shouldCorrectSaveBookIfAuthorAndGenreNotExist() {
        long expectedId = 1L;
        when(genreDao.isExists(anyString())).thenReturn(false);
        when(authorDao.isExists(anyString())).thenReturn(false);
        when(bookDao.insert(any())).thenReturn(1L);

        long newId = bookService.save(testedBook);

        verify(genreDao, times(1)).insert(any());
        verify(authorDao, times(1)).insert(any());
        assertThat(expectedId).isEqualTo(newId);

    }

    @DisplayName("корректно сохранять книгу если автор уже существует")
    @Test
    void shouldCorrectSaveBookIfAuthorExists() {

        when(genreDao.isExists(anyString())).thenReturn(false);
        when(authorDao.isExists(anyString())).thenReturn(true);

        bookService.save(testedBook);

        verify(genreDao, times(1)).insert(any());
        verify(authorDao, times(0)).insert(any());

    }

    @DisplayName("корректно сохранять книгу если жанр уже существует")
    @Test
    void shouldCorrectSaveBookIfGenreExists() {

        when(genreDao.isExists(anyString())).thenReturn(true);
        when(authorDao.isExists(anyString())).thenReturn(true);

        bookService.save(testedBook);

        verify(genreDao, times(0)).insert(any());
        verify(authorDao, times(0)).insert(any());
    }

    @DisplayName("корректно обновлять автора книги если автора не существует")
    @Test
    void shouldCorrectUpdateAuthorOfBooksIfAuthorNotExists() {
        when(genreDao.isExists(anyString())).thenReturn(true);
        when(authorDao.isExists(anyString())).thenReturn(false);

        bookService.update(testedBook, testedBook.getId());

        verify(genreDao, times(0)).insert(any());
        verify(authorDao, times(1)).insert(any());
    }

    @DisplayName("корректно обновлять жанр книги если жанр не существует")
    @Test
    void shouldCorrectUpdateGenreOfBooksIfGenreNotExists() {

        when(genreDao.isExists(anyString())).thenReturn(false);
        when(authorDao.isExists(anyString())).thenReturn(true);

        bookService.update(testedBook, testedBook.getId());

        verify(genreDao, times(1)).insert(any());
        verify(authorDao, times(0)).insert(any());
    }

    @DisplayName("бросать исключение с ожидаемым сообщением при операции получения если книга не найдена")
    @Test
    void shouldThrowExceptionWithExpectedMessageWhenGettingIfBookNotFound() {
        when(bookDao.getById(anyLong())).thenThrow(new EmptyResultDataAccessException(1));

        assertThatThrownBy(() -> bookService.getById(1000)).hasMessage("Книга не найдена");
    }

    @DisplayName("бросать исключение с ожидаемым сообщением при операции обновления если книга не найдена")
    @Test
    void shouldThrowExceptionWithExpectedMessageWhenUpdatingIfBookNotFound() {
        long notExistsId = 1000;
        when(bookDao.getById(anyLong())).thenThrow(new EmptyResultDataAccessException(1));
        String expectedMessage = String.format("Книга с ид %s не найдена", notExistsId);
        assertThatThrownBy(() -> bookService.update(Book.builder().build(), notExistsId)).hasMessage(expectedMessage);
    }

    @DisplayName("должен удалять автора и жанр, по которым нет книг")
    @Test
    void shouldDeleteAuthorAndGenreWithoutBooks() {

        when(bookDao.getById(anyLong())).thenReturn(testedBook);
        when(bookDao.findByAuthor(anyString())).thenReturn(new ArrayList<>());
        when(bookDao.findByGenre(anyString())).thenReturn(new ArrayList<>());

        long scriptBookId = 1;
        bookService.deleteById(scriptBookId);

        verify(authorDao, times(1)).deleteById(anyLong());
        verify(genreDao, times(1)).delete(anyString());

    }
}