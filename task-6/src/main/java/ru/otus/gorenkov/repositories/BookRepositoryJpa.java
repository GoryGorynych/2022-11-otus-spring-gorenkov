package ru.otus.gorenkov.repositories;

import org.springframework.stereotype.Component;
import ru.otus.gorenkov.models.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

@Component
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
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre", Book.class);
        setAuthorsEntityGraph(query);
        return query.getResultList();
    }

    @Override
    public List<Book> findByAuthor(String fullName) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre " +
                "where b.author.fullName = :name", Book.class);
        query.setParameter("name", fullName);
        setAuthorsEntityGraph(query);
        return query.getResultList();
    }

    @Override
    public List<Book> findByGenre(String genre) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.genre " +
                "where b.genre.genre = :genre", Book.class);
        query.setParameter("genre", genre);
        setAuthorsEntityGraph(query);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() <= 0
                && book.getAuthor().getId() <= 0
                && book.getGenre().getId() <= 0) {
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

    private void setAuthorsEntityGraph(Query query) {
        EntityGraph entityGraph = em.getEntityGraph("authors-entity-graph");
        query.setHint(EntityGraphType.FETCH.getKey(), entityGraph);
    }
}
