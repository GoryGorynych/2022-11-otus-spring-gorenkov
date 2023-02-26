package ru.otus.gorenkov.repositories;

import org.springframework.stereotype.Repository;
import ru.otus.gorenkov.models.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

@Repository
public class BookRepositoryJpa implements BookRepository{

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
       this.em = em;
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        EntityGraph entityGraph = em.getEntityGraph("authors-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre left join fetch b.comments", Book.class);
        query.setHint(EntityGraphType.FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> findByAuthor(String fullName) {
        EntityGraph entityGraph = em.getEntityGraph("authors-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre left join fetch b.comments " +
                "where b.author.fullName = :name", Book.class);
        query.setParameter("name", fullName);
        query.setHint(EntityGraphType.FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public List<Book> findByGenre(String genre) {
        EntityGraph entityGraph = em.getEntityGraph("authors-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre left join fetch b.comments " +
                "where b.genre.genre = :genre", Book.class);
        query.setParameter("genre", genre);
        query.setHint(EntityGraphType.FETCH.getKey(), entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteById(long id) {
        Book book = em.find(Book.class, id);
        em.remove(book);
    }
}
