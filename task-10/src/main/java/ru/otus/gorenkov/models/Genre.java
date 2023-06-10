package ru.otus.gorenkov.models;

import lombok.Data;

@Data
public class Genre {

    private String title;

    private String decription;

    public Genre(String title) {
        this.title = title;
    }
}
