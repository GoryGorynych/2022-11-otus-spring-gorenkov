package ru.otus.gorenkov.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Builder
@Data
public class Book {

    private final long id;
    private String name;
    private Author author;
    private Genre genre;
    private Date publicationDate;
}
