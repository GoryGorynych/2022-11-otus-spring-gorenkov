package ru.otus.gorenkov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.repositories.AuthorRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами должен ")
@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    TestEntityManager em;

    @Autowired
    AuthorRepository authorRepository;

    @DisplayName("находить правильного автора по полному имени")
    @Test
    void shouldFindCorrectAuthorByFullName() {
        Author author_01 = authorRepository.findByFullName("author_01");
        Author author = em.find(Author.class, 1L);
        assertThat(author).usingRecursiveComparison().isEqualTo(author_01);

    }
}
