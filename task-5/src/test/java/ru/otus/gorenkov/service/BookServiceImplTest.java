package ru.otus.gorenkov.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.gorenkov.dao.AuthorDaoJdbc;
import ru.otus.gorenkov.dao.BookDaoJdbc;
import ru.otus.gorenkov.dao.GenreDaoJdbc;
import ru.otus.gorenkov.domain.Author;
import ru.otus.gorenkov.domain.Book;
import ru.otus.gorenkov.domain.Genre;
import ru.otus.gorenkov.service.BookServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Сервис для книг должен ")
@JdbcTest
@Import({BookServiceImpl.class, BookDaoJdbc.class, GenreDaoJdbc.class, AuthorDaoJdbc.class})
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private BookDaoJdbc bookDao;
    @Autowired
    private GenreDaoJdbc genreDao;
    @Autowired
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

        long newId = bookService.save(testedBook);
        Book actualBook = bookService.getById(newId);

        assertThat(actualBook).usingRecursiveComparison().ignoringFieldsMatchingRegexes("id", "author.id").isEqualTo(testedBook);
    }

    @DisplayName("корректно сохранять книгу если автор уже существует")
    @Test
    void shouldCorrectSaveBookIfAuthorExists() {

        authorDao.insert(testedAuthor);
        long newId = bookService.save(testedBook);
        Book actualBook = bookService.getById(newId);

        assertThat(actualBook).usingRecursiveComparison().ignoringFieldsMatchingRegexes("id", "author.id").isEqualTo(testedBook);
    }

    @DisplayName("корректно сохранять книгу если жанр уже существует")
    @Test
    void shouldCorrectSaveBookIfGenreExists() {

        genreDao.insert(testedGenre);
        long newId = bookService.save(testedBook);
        Book actualBook = bookService.getById(newId);

        assertThat(actualBook).usingRecursiveComparison().ignoringFieldsMatchingRegexes("id", "author.id").isEqualTo(testedBook);
    }

    @DisplayName("корректно обновлять автора книги если автора не существует")
    @Test
    void shouldCorrectUpdateAuthorOfBooksIfAuthorNotExists() {
        Book expectedBook = bookService.getById(1);
        expectedBook.getAuthor().setFullName("New Author");

        bookService.update(expectedBook, expectedBook.getId());
        Book resultBook = bookService.getById(1);

        assertThat(resultBook).usingRecursiveComparison().ignoringFieldsMatchingRegexes("author.id").isEqualTo(expectedBook);
    }

    @DisplayName("корректно обновлять жанр книги если жанр не существует")
    @Test
    void shouldCorrectUpdateGenreOfBooksIfGenreNotExists() {
        Book expectedBook = bookService.getById(1);
        expectedBook.setGenre(Genre.builder().genre("New Genre").build());

        bookService.update(expectedBook, expectedBook.getId());
        Book resultBook = bookService.getById(1);

        assertThat(resultBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("бросать исключение с ожидаемым сообщением при операции получения если книга не найдена")
    @Test
    void shouldThrowExceptionWithExpectedMessageWhenGettingIfBookNotFound() {
        assertThatThrownBy(() -> bookService.getById(1000)).hasMessage("Книга не найдена");
    }

    @DisplayName("бросать исключение с ожидаемым сообщением при операции обновления если книга не найдена")
    @Test
    void shouldThrowExceptionWithExpectedMessageWhenUpdatingIfBookNotFound() {
        long notExistsId = 1000;
        String expectedMessage = String.format("Книга с ид %s не найдена", notExistsId);
        assertThatThrownBy(() -> bookService.update(Book.builder().build(), notExistsId)).hasMessage(expectedMessage);
    }

    @DisplayName("должен удалять автора и жанр, по которым нет книг")
    @Test
    void shouldDeleteAuthorAndGenreWithoutBooks() {
        long scriptBookId = 1;
        Book scriptBook = bookService.getById(scriptBookId);
        bookService.deleteById(scriptBookId);

        assertThatThrownBy(() -> authorDao.getById(scriptBook.getId())).isInstanceOf(EmptyResultDataAccessException.class);
        assertThatThrownBy(() -> genreDao.get(scriptBook.getGenre().getGenre())).isInstanceOf(EmptyResultDataAccessException.class);

    }
}