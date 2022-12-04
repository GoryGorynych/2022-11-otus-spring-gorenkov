package ru.otus.gorenkov.service;

import ru.otus.gorenkov.domain.QuestionCard;

import java.util.List;

public interface QuestionService {

    List<QuestionCard> getAllQuestionCards();

    void printAllQuestionCards();
}
