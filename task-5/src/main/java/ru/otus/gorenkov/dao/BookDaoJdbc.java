package ru.otus.gorenkov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.gorenkov.domain.Author;
import ru.otus.gorenkov.domain.Book;
import ru.otus.gorenkov.domain.Genre;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    private static RowMapper<Book> bookRowMapper = (rs, rowNum) -> {

        Author author = Author.builder()
                .id(rs.getLong("author_id"))
                .fullName(rs.getString("fullname"))
                .birthYear(rs.getInt("birthyear"))
                .deathyear(rs.getInt("deathyear"))
                .birthcountry(rs.getString("birthcountry"))
                .build();

        Genre genre = Genre.builder()
                .genre(rs.getString("genre"))
                .decription(rs.getString("description"))
                .build();

        Book book = Book.builder()
                .id(rs.getLong("book_id"))
                .name(rs.getString("name"))
                .author(author)
                .genre(genre)
                .publicationDate(rs.getDate("publicationdate"))
                .build();

        return book;
    };

    @Override
    public long insert(Book book) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_fullname", book.getAuthor().getFullName());
        params.addValue("genre", book.getGenre().getGenre());
        params.addValue("publicationdate", book.getPublicationDate());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update("insert into books (name, author_fullname, genre, publicationdate) " +
                        "values (:name, :author_fullname, :genre, :publicationdate)",
                params, keyHolder, new String[] {"id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject(
                "select b.id as book_id, b.name, a.id as author_id, a.fullname, a.birthyear, a.deathyear, " +
                        "a.birthcountry, b.genre, g.description, b.publicationdate  " +
                        "from books b inner join authors a on b.author_fullname = a.fullname " +
                        "inner join genres g on b.genre = g.genre where b.id = :id",
                Map.of("id", id), bookRowMapper);
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select b.id as book_id, b.name, a.id as author_id, a.fullname, a.birthyear, a.deathyear, " +
                        "a.birthcountry, b.genre, g.description, b.publicationdate  " +
                        "from books b inner join authors a on b.author_fullname = a.fullname " +
                        "inner join genres g on b.genre = g.genre",
                bookRowMapper);
    }

    @Override
    public void update(Book book, long id) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_fullname", book.getAuthor().getFullName());
        params.addValue("genre", book.getGenre().getGenre());
        params.addValue("publicationdate", book.getPublicationDate());
        params.addValue("id", id);

        jdbc.update("update books set name = :name, author_fullname = :author_fullname, genre = :genre, " +
                        "publicationdate = :publicationdate where id = :id",
                params);
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id",
                Map.of("id", id));
    }

    @Override
    public List<Book> findByAuthor(String authorName) {
        return jdbc.query(
                "select b.id as book_id, b.name, a.id as author_id, a.fullname, a.birthyear, a.deathyear, " +
                        "a.birthcountry, b.genre, g.description, b.publicationdate  " +
                        "from books b inner join authors a on b.author_fullname = a.fullname " +
                        "inner join genres g on b.genre = g.genre where a.fullname = :authorName",
                Map.of("authorName", authorName), bookRowMapper);
    }

    @Override
    public List<Book> findByGenre(String genre) {
        return jdbc.query(
                "select b.id as book_id, b.name, a.id as author_id, a.fullname, a.birthyear, a.deathyear, " +
                        "a.birthcountry, b.genre, g.description, b.publicationdate  " +
                        "from books b inner join authors a on b.author_fullname = a.fullname " +
                        "inner join genres g on b.genre = g.genre where g.genre = :genre",
                Map.of("genre", genre), bookRowMapper);
    }
}
