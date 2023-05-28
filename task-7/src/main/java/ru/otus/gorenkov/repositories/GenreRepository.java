package ru.otus.gorenkov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.gorenkov.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findByGenre(String genre);

}
