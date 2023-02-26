insert into genres(genre)
values ('genre_01'), ('genre_02'), ('genre_03'), ('genre_04'), ('genre_05');

insert into authors(fullname)
values ('author_01'), ('author_02'), ('author_03'), ('author_04'), ('author_05'),
    ('author_06'), ('author_07'), ('author_08'), ('author_09'), ('author_10');

insert into books(name, author_id, genre_id)
values ('book_01', 1, 1), ('book_01', 2, 2), ('book_01', 3, 3), ('book_01', 4, 4), ('book_01', 5, 5),
    ('book_01', 6, 1), ('book_01', 7, 2), ('book_01', 8, 3), ('book_01', 9, 4), ('book_01', 10, 5);

insert into bookcomments(nickname, text, book_id)
values ('user_01', 'comment_01', 1), ('user_02', 'comment_02', 2), ('user_03', 'comment_03', 3), ('user_04', 'comment_04', 4),
       ('user_05', 'comment_05', 5), ('user_06', 'comment_06', 6), ('user_07', 'comment_07', 7), ('user_08', 'comment_08', 8),
       ('user_09', 'comment_09', 9), ('user_10', 'comment_10', 10);