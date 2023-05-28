package ru.otus.gorenkov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "birthyear")
    private Integer birthYear;

    @Column(name = "deathyear")
    private Integer deathyear;

    @Column(name = "birthcountry")
    private String birthcountry;

}
