package ru.otus.gorenkov.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.gorenkov.models.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@RequiredArgsConstructor
@Component
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Author findByFullName(String fullName) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.fullName = :fullname", Author.class);
        query.setParameter("fullname", fullName);

        return query.getSingleResult();
    }
}
