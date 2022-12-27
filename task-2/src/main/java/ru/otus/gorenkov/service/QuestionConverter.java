package ru.otus.gorenkov.service;

import ru.otus.gorenkov.domain.QuestionCard;

import java.util.List;

public interface QuestionConverter {

    List<QuestionCard> convertRawDataToQuestionCards(List<String[]> allLines);

    String convertQuestionCardToString(QuestionCard questionCard);

}
