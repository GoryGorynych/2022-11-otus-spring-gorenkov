package ru.otus.gorenkov.exception;

public class NotFoundBookException extends RuntimeException{
    public NotFoundBookException(String message) {
        super(message);
    }
}
