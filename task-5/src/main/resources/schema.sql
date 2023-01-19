drop table if exists genres;
create table genres
(
    genre       nvarchar(50) PRIMARY KEY,
    description nvarchar(255) NULL
);

drop table if exists authors;
create table authors
(
    id           IDENTITY PRIMARY KEY,
    fullname     nvarchar(150) NOT NULL,
    birthyear    smallint NULL,
    deathyear    smallint NULL ,
    birthcountry nvarchar(50) NULL
);

drop table if exists books;
create table books
(
    id              IDENTITY PRIMARY KEY,
    name            nvarchar(255) NOT NULL,
    author_id       bigint NOT NULL,
    genre           nvarchar(50)  NOT NULL,
    publicationdate DATE          NULL,
    FOREIGN KEY(author_id) REFERENCES authors(id),
    FOREIGN KEY(genre) REFERENCES genres(genre),
    UNIQUE (name, author_id)
);