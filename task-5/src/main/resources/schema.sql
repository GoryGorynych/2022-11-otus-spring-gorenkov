drop table if exists genres;
create table genres
(
    genre       nvarchar(50) PRIMARY KEY,
    description nvarchar(255) NULL
);

drop table if exists authors;
create table authors
(
    id           bigint auto_increment,
    fullname     nvarchar(150) PRIMARY KEY,
    birthyear    smallint NULL,
    deathyear    smallint NULL ,
    birthcountry nvarchar(50) NULL
);

drop table if exists books;
create table books
(
    id              IDENTITY PRIMARY KEY,
    name            nvarchar(255) NOT NULL,
    author_fullname nvarchar(150) NOT NULL,
    genre           nvarchar(50)  NOT NULL,
    publicationdate DATE          NULL,
    FOREIGN KEY(author_fullname) REFERENCES authors(fullname),
    FOREIGN KEY(genre) REFERENCES genres(genre),
    UNIQUE (name, author_fullname)
);