drop table if exists genres;
create table genres
(
    id IDENTITY PRIMARY KEY,
    genre       nvarchar(50) NOT NULL,
    description nvarchar(255) NULL,
    UNIQUE (genre)
);

drop table if exists authors;
create table authors
(
    id IDENTITY PRIMARY KEY,
    fullname     nvarchar(150) NOT NULL,
    birthyear    smallint NULL,
    deathyear    smallint NULL,
    birthcountry nvarchar(50) NULL,
    UNIQUE (fullname)
);

drop table if exists books;
create table books
(
    id IDENTITY PRIMARY KEY,
    name            nvarchar(255) NOT NULL,
    author_id       bigint NOT NULL,
    genre_id        bigint NOT NULL,
    publicationdate DATE NULL,
    FOREIGN KEY (author_id) REFERENCES authors (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id),
    UNIQUE (name)
);

drop table if exists bookcomments;
create table bookcomments
(
    id IDENTITY PRIMARY KEY,
    nickname nvarchar(50) NOT NULL,
    text     nvarchar( max) NULL,
    book_id  bigint NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books (id)
)