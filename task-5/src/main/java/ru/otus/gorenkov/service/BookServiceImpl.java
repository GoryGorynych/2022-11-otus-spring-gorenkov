package ru.otus.gorenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.gorenkov.dao.AuthorDao;
import ru.otus.gorenkov.dao.BookDao;
import ru.otus.gorenkov.dao.GenreDao;
import ru.otus.gorenkov.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private final BookDao bookDao;

    @Override
    public long save(Book book) {

        if (!genreDao.isExists(book.getGenre().getGenre())) {
            genreDao.insert(book.getGenre());
        }
        if (!authorDao.isExists(book.getAuthor().getFullName())) {
            authorDao.insert(book.getAuthor());
        }

        long bookId = bookDao.insert(book);
        return bookId;
    }

    @Override
    public Book getById(long id) {
        Book book;
        try {
            book = bookDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("Книга не найдена", e);
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void update(Book book, long id) {

        try {
            bookDao.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(String.format("Книга с ид %s не найдена", id), e);
        }

        if (!genreDao.isExists(book.getGenre().getGenre())) {
            genreDao.insert(book.getGenre());
        }
        if (!authorDao.isExists(book.getAuthor().getFullName())) {
            authorDao.insert(book.getAuthor());
        }

        bookDao.update(book, id);
    }

    @Override
    public void deleteById(long id) {
        Book book = this.getById(id);
        bookDao.deleteById(id);

        if (bookDao.findByAuthor(book.getAuthor().getFullName()).size() == 0) {
            authorDao.deleteById(book.getAuthor().getId());
        }
        if (bookDao.findByGenre(book.getGenre().getGenre()).size() == 0) {
            genreDao.delete(book.getGenre().getGenre());
        }
    }
}
