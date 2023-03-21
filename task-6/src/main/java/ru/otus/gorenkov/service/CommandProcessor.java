package ru.otus.gorenkov.service;

public interface CommandProcessor {

    String showAll();
    String showById(long id);
    String add(String bookName, String authorName, String genre);
    String edit(long id, String bookName, String authorName, String genre);
    String delete(long id);
    String addComment(long bookId, String nickName, String text);
    String editComment(long id, String text);
    String deleteAllCommentsByBook(long bookId);
    String showAllCommentsByBook(long bookId);
}
