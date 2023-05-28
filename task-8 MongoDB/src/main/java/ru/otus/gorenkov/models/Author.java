package ru.otus.gorenkov.models;

import lombok.Data;

@Data
public class Author {

    private String fullName;

    private Integer birthYear;

    private Integer deathyear;

    private String birthcountry;

    public Author(String fullName) {
        this.fullName = fullName;
    }

}
