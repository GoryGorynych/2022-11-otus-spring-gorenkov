package ru.otus.gorenkov;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.gorenkov.models.Author;
import ru.otus.gorenkov.models.Book;
import ru.otus.gorenkov.models.Genre;
import ru.otus.gorenkov.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository repositoryJpa;

    @Autowired
    TestEntityManager em;

    private static final int EXPECTED_NUMBER_BOOKS = 10;
    private static final int EXPECTED_QUERIES_COUNT = 2;

    @DisplayName("загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectStudentsListWithAllInfo() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        List<Book> books = repositoryJpa.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_BOOKS)
                .allMatch(s -> !"".equals(s.getName()))
                .allMatch(s -> s.getGenre() != null && !"".equals(s.getGenre().getGenre()))
                .allMatch(s -> s.getAuthor() != null && !"".equals(s.getAuthor().getFullName()))
                .allMatch(s -> s.getComments() != null && s.getComments().size() > 0);

//        System.out.println("Statement count = " + sessionFactory.getStatistics().getPrepareStatementCount());
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

}