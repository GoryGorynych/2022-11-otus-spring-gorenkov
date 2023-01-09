package ru.otus.gorenkov.service;

public interface IOService {

    void out(String messgae);
    String readString();

    void out(String message, Object[] params);
}
