package ru.otus.gorenkov.service;

public interface CommandProcessor {

    String showAll();
    String showById(long id);
    String add(String ... attributes);
    String edit(long id, String... attributes);
    String delete(long id);
    String addComment(Object... attributes);
    String editComment(long id, String text);
    String deleteAllCommentsByBook(long bookId);
    String showAllCommentsByBook(long bookId);
}
