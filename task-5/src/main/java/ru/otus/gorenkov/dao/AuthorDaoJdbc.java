package ru.otus.gorenkov.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.gorenkov.domain.Author;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    private static RowMapper<Author> authorRowMapper = (rs, rowNum) ->
            Author.builder()
                    .id(rs.getLong("id"))
                    .fullName(rs.getString("fullname"))
                    .birthYear(rs.getInt("birthyear"))
                    .deathyear(rs.getInt("deathyear"))
                    .birthcountry(rs.getString("birthcountry"))
                    .build();

    @Override
    public long insert(Author author) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("fullname", author.getFullName());
        params.addValue("birthyear", author.getBirthYear());
        params.addValue("deathyear", author.getDeathyear());
        params.addValue("birthcountry", author.getBirthcountry());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update("insert into authors (fullname, birthyear, deathyear, birthcountry) " +
                        "values (:fullname, :birthyear, :deathyear, :birthcountry)",
                params, keyHolder, new String[] {"id"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject(
                "select id, fullname, birthyear, deathyear, birthcountry from authors where id = :id",
                Map.of("id", id), authorRowMapper );
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, fullname, birthyear, deathyear, birthcountry from authors",
                authorRowMapper);
    }

    @Override
    public void update(Author author, long id) {

        Map<String, Object> params = new HashMap<>();
        params.put("fullname", author.getFullName());
        params.put("birthyear", author.getBirthYear());
        params.put("deathyear", author.getDeathyear());
        params.put("birthcountry", author.getBirthcountry());
        params.put("id", id);

        jdbc.update("update authors set fullname = :fullname, birthyear = :birthyear, deathyear = :deathyear, " +
                "birthcountry = :birthcountry where id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

    @Override
    public boolean isExists(String fullName) {
        int count = jdbc.queryForObject("select count(1) from authors where fullname = :fullname",
                Map.of("fullname", fullName), Integer.class);
        return count > 0;
    }
}
