package ru.otus.gorenkov.service;

public interface CommandProcessor {

    String showAll();
    String showById(long id);
    String add(String ... attributes);
    String edit(long id, String... attributes);
    String delete(long id);
}
