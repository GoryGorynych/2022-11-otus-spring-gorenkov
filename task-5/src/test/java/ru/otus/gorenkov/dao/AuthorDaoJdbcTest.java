package ru.otus.gorenkov.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.gorenkov.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для авторов должно ")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc dao;

    private Author fakeAuthor;
    private Author scriptAuthor;

    @BeforeEach
    public void before() {
        fakeAuthor = Author.builder().fullName("Тестовый автор").birthYear(1900).deathyear(2000).birthcountry("Япония").build();
        scriptAuthor = Author.builder().id(1).fullName("Скриптовый автор").birthYear(1900).deathyear(1999).build();
    }

    @DisplayName("корректно инсертить автора")
    @Test
    void shouldCorrectInsertAuthor() {
        dao.insert(fakeAuthor);
        assertThat(dao.isExists(fakeAuthor.getFullName())).isTrue();
    }

    @DisplayName("возвращать ожидаемого автора")
    @Test
    void shouldReturnExpectedAuthor() {
        Author actualAuthor = dao.getById(scriptAuthor.getId());
        assertThat(scriptAuthor).usingRecursiveComparison().isEqualTo(actualAuthor);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> authorList = dao.getAll();
        assertThat(authorList).containsExactlyInAnyOrder(scriptAuthor);
    }

    @DisplayName("корректно обновлять автора")
    @Test
    void shouldCorrectUpdateAuthor() {
        Author expectedAuthor = dao.getById(scriptAuthor.getId());
        expectedAuthor.setBirthcountry("Russia");
        dao.update(expectedAuthor, scriptAuthor.getId());
        Author actualAuthor = dao.getById(scriptAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("должен удалять автора без связей")
    @Test
    void shouldDeleteAuthorWithoutReference() {
        long id = dao.insert(fakeAuthor);
        dao.deleteById(id);
        assertThatThrownBy(() -> dao.getById(id)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать true если автор существует")
    @Test
    void shouldReturnTrueIfAuthorExists() {
        assertThat(dao.isExists(scriptAuthor.getFullName())).isTrue();
    }
}