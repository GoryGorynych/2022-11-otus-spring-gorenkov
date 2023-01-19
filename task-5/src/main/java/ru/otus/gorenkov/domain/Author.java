package ru.otus.gorenkov.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Author {

    private long id;
    private String fullName;
    private int birthYear;
    private int deathyear;
    private String birthcountry;

}
