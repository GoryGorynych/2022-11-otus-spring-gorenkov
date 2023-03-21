package ru.otus.gorenkov.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.gorenkov.models.Genre;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@RequiredArgsConstructor
@Component
public class GenreRepositoryJpa implements GenreRepository{

    private final EntityManager em;

    @Override
    public Genre getGenreByGenre(String genre) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where genre = :genre", Genre.class);
        query.setParameter("genre", genre);
        return query.getSingleResult();
    }
}
