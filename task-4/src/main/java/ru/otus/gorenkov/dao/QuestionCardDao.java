package ru.otus.gorenkov.dao;

import ru.otus.gorenkov.domain.QuestionCard;

import java.util.List;

public interface QuestionCardDao {

    List<QuestionCard> getAll();

    QuestionCard getQuestionCardById(int id);

}
