package ru.otus.gorenkov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.gorenkov.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для жанров должно ")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("возвращать ожидаемый жанр")
    @Test
    public void shouldReturnExpectedGenre() {
        Genre expectedGenre = Genre.builder().genre("фэнтези").build();
        Genre actualGenre = genreDao.get("фэнтези");
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("сохранять новый жанр")
    @Test
    public void shouldSaveGenre() {
        Genre expectedGenre = Genre.builder().genre("боевик").build();
        genreDao.insert(expectedGenre);

        Genre actualGenre = genreDao.get(expectedGenre.getGenre());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    public void shouldReturnExpectedGenreList() {
        Genre expectedGenre = Genre.builder().genre("фэнтези").build();
        List<Genre> all = genreDao.getAll();
        assertThat(all).containsExactlyInAnyOrder(expectedGenre);
    }

    @DisplayName("обновлять описание жанра")
    @Test
    public void shouldUpdateDescriptionOfGenre() {
        Genre expectedGenre = Genre.builder().genre("фэнтези").decription("Сказочки там всякие").build();
        genreDao.update(expectedGenre);
        Genre actualGenre = genreDao.get(expectedGenre.getGenre());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(actualGenre);
    }

    @DisplayName("удалять жанр без связей")
    @Test
    public void shouldDeleteGenreWithoutRelation() {
        Genre newGenre = Genre.builder().genre("боевик").build();
        genreDao.insert(newGenre);
        genreDao.delete(newGenre.getGenre());
        assertThatThrownBy(() -> genreDao.get(newGenre.getGenre())).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать true если жанр существует")
    @Test
    public void shouldTrueIfExistsGenre() {
        boolean exists = genreDao.isExists("фэнтези");
        assertThat(exists).isTrue();
    }
}