package ru.otus.gorenkov.repositories;

import org.springframework.stereotype.Component;
import ru.otus.gorenkov.models.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component
public class CommentRepositoryJpa implements CommentRepository{

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment findById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.find(Comment.class, id));
    }

    @Override
    public void deleteByBookId(long bookId) {
        Query query = em.createQuery("delete from Comment c where c.bookId = :bookId");
        query.setParameter("bookId", bookId);
        query.executeUpdate();
    }
}
