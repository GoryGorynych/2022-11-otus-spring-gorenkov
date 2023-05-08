package ru.otus.gorenkov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Genre {

    private String title;

    private String decription;

    public Genre(String title) {
        this.title = title;
    }
}
