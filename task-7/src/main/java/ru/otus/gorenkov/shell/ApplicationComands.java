package ru.otus.gorenkov.shell;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.gorenkov.service.CommandProcessor;

import java.sql.SQLException;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationComands {

    private final CommandProcessor bookProcessor;

    @ShellMethod(value = "Run h2 console", key = {"dbconsole", "dbc"})
    public String dbConsole() throws SQLException {
        Console.main(null);
        return "Database console running";
    }

    @ShellMethod(value = "Show all books", key = {"showall", "sa"})
    public String showAllBooks() {
        return bookProcessor.showAll();
    }

    @ShellMethod(value = "Show book by id.", key = {"showid", "sid"})
    public String showBookById(@ShellOption(defaultValue = "0") long id) {
        return bookProcessor.showById(id);
    }

    @ShellMethod(value = "Add book", key = {"add", "a"})
    public String addBook(@ShellOption(defaultValue = "") String bookName,
                          @ShellOption(defaultValue = "") String authorName,
                          @ShellOption(defaultValue = "")String genre) {

        return bookProcessor.add(bookName, authorName, genre);
    }

    @ShellMethod(value = "Edit book by id.", key = {"edit", "e"})
    public String editBook(@ShellOption(defaultValue = "0") long id,
                           @ShellOption(defaultValue = "") String bookName,
                           @ShellOption(defaultValue = "") String authorName,
                           @ShellOption(defaultValue = "")String genre) {

        return bookProcessor.edit(id, bookName, authorName, genre);
    }

    @ShellMethod(value = "Delete book by id.", key = {"delete", "d"})
    public String deleteBook(@ShellOption(defaultValue = "0") long id) {
        return bookProcessor.delete(id);
    }

    @ShellMethod(value = "Add comment to book", key = {"comment", "com"})
    public String addComment(@ShellOption(defaultValue = "0") long bookId,
                             @ShellOption String nickName,
                             @ShellOption String text) {
        return bookProcessor.addComment(bookId, nickName, text);
    }

    @ShellMethod(value = "Edit comment by id", key = {"editcom", "ecom"})
    public String editComment(@ShellOption (defaultValue = "0") long id, @ShellOption String text) {
        return bookProcessor.editComment(id, text);
    }

    @ShellMethod(value = "Delete all comments by book", key = {"delcom", "dcom"})
    public String deleteComments(@ShellOption (defaultValue = "0") long bookId) {
        return bookProcessor.deleteAllCommentsByBook(bookId);
    }

    @ShellMethod(value = "Show all comments by book", key = {"showcom", "scom"})
    public String showComments(@ShellOption (defaultValue = "0") long bookId) {
        return bookProcessor.showAllCommentsByBook(bookId);
    }
}
