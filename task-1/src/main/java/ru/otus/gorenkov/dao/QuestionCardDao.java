package ru.otus.gorenkov.dao;

import ru.otus.gorenkov.domain.QuestionCard;

import java.util.List;

public interface QuestionCardDao {

    List<QuestionCard> getAllQuestionCards();

    QuestionCard getQuestionCardById(int id);

}
