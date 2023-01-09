package ru.otus.gorenkov.service;

public interface MessageServise {

    String getMessage(String code, Object[] params);
    String getMessage(String code);
}
