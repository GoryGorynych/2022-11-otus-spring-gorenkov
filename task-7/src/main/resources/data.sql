insert into genres(genre)
values ('фэнтези'), ('роман'), ('ужасы'), ('детектив'), ('повесть');

insert into authors(fullname)
values ('Джон Роналд Толкин'), ('Маргарет Митчелл'), ('Брэм Стокер'), ('Артур Конан Дойль'), ('Эрнест Хемингуэй');

insert into books(name, author_id, genre_id)
values ('Властелин колец', 1, 1), ('Унесённые ветром', 2, 2), ('Дракула', 3, 3), ('Собака Баскервилей', 4, 4), ('Старик и море', 5, 5);

insert into bookcomments(nickname, text, book_id)
values ('user_01', 'comment_01', 1), ('user_02', 'comment_02', 1), ('user_03', 'comment_03', 2), ('user_04', 'comment_04', 2),
       ('user_05', 'comment_05', 3), ('user_06', 'comment_06', 3), ('user_07', 'comment_07', 4), ('user_08', 'comment_08', 4),
       ('user_09', 'comment_09', 5), ('user_10', 'comment_10', 5);