package ru.otus.gorenkov.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Genre {

    private final String genre;
    private String decription;
}
