package ru.otus.gorenkov.exception;

public class CorrectAnswerNotFoundException extends RuntimeException{

    public CorrectAnswerNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
