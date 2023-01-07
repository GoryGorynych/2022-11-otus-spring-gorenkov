package ru.otus.gorenkov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.gorenkov.domain.Genre;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    private static RowMapper<Genre> genreRowMapper =
            (rs, rowNum) -> Genre.builder()
                    .genre(rs.getString("genre"))
                    .decription(rs.getString("description"))
                    .build();

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into genres (genre) values (:genre)",
                Map.of("genre", genre.getGenre()));
    }

    @Override
    public Genre get(String genre) {
        return jdbc.queryForObject("select genre, description from genres where genre = :genre",
                Map.of("genre", genre), genreRowMapper);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.getJdbcOperations().query("select genre, description from genres",
                genreRowMapper);
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("update genres set description = :description where genre = :genre",
                Map.of("description", genre.getDecription(), "genre", genre.getGenre()));
    }

    @Override
    public void delete(String genre) {
        jdbc.update("delete from genres where genre = :genre",
                Map.of("genre", genre));
    }

    @Override
    public boolean isExists(String genre) {
        Integer count = jdbc.queryForObject("select count(1) from genres where genre = :genre",
                Map.of("genre", genre), Integer.class);
        return count > 0;
    }
}
