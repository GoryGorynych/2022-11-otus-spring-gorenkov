package ru.otus.gorenkov.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Builder
@Getter
@Setter
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String name;

    private Date publicationDate;

//    public Book(String name) {
//        this.name = name;
//    }

    private Author author;

    private Genre genre;

}
