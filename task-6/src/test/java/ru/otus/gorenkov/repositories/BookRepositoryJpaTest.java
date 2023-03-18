package ru.otus.gorenkov.repositories;

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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий JPA для работы с книгами ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    BookRepositoryJpa repositoryJpa;

    @Autowired
    TestEntityManager em;

    private static final long FIRST_BOOK_ID = 1L;
    private static final int EXPECTED_NUMBER_BOOKS = 10;
    private static final int EXPECTED_QUERIES_COUNT = 2;

    @DisplayName("должен загружать информацию о нужной книге по ее ид")
    @Test
    void shouldFindExpectedBookById() {
        Optional<Book> optionalActualBook = repositoryJpa.findById(FIRST_BOOK_ID);
        Book optionalExpectedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(optionalActualBook).isPresent().get().usingRecursiveComparison().isEqualTo(optionalExpectedBook);

    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
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

    @DisplayName("должен загружать нужную книгу по автору")
    @Test
    void shouldReturnCorrectBookByAuthor() {
        List<Book> books = repositoryJpa.findByAuthor("author_02");
        Book actualBook = books.get(0);
        Book expectedBook = em.find(Book.class, 2L);
        assertThat(expectedBook).isNotNull().usingRecursiveComparison().isEqualTo(actualBook);
    }

    @DisplayName("должен загружать корректный список книг по жанру")
    @Test
    void shouldReturnCorrectBookListByGenre() {
        String genre = "genre_03";
        List<Book> books = repositoryJpa.findByGenre(genre);
        assertThat(books).isNotNull().hasSize(2)
                .allMatch(s -> s.getGenre().getGenre().equals(genre));
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        Book newBook = new Book();
        newBook.setName("book_20");
        newBook.setAuthor(Author.builder().fullName("author_20").build());
        newBook.setGenre(Genre.builder().genre("genre_20").build());

        Book savedBook = repositoryJpa.save(newBook);
        em.detach(newBook);
        Book actualBook = em.find(Book.class, savedBook.getId());
        actualBook.setComments(null);

        assertThat(actualBook).isNotNull().usingRecursiveComparison().isEqualTo(savedBook);
    }

    @DisplayName("должен обновлять название книги")
    @Test
    void shouldUpdateBookName() {

        String newBookName = "book_newname";
        Book book = em.find(Book.class, FIRST_BOOK_ID);
        book.setName(newBookName);
        em.detach(book);
        repositoryJpa.save(book);
        Book actualBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(actualBook.getName()).isEqualTo(newBookName);
    }

    @DisplayName("должен удалять книгу по ид")
    @Test
    void shouldRemoveBookById() {
        Book firstBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);
        repositoryJpa.deleteById(FIRST_BOOK_ID);
        Book deleteBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(deleteBook).isNull();
    }

}