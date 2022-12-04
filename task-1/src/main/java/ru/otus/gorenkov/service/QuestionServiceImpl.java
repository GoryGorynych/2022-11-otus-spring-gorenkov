package ru.otus.gorenkov.service;

import ru.otus.gorenkov.dao.QuestionCardDao;
import ru.otus.gorenkov.domain.QuestionCard;

import java.util.List;

public class QuestionServiceImpl implements QuestionService{

    private final QuestionCardDao questionDao;
    private final QuestionConverter converter;

    public QuestionServiceImpl(QuestionCardDao questionDao, QuestionConverter converter) {
        this.questionDao = questionDao;
        this.converter = converter;
    }

    @Override
    public List<QuestionCard> getAllQuestionCards() {
        return questionDao.getAll();
    }

    @Override
    public void printAllQuestionCards() {
        questionDao.getAll()
                .forEach(questionCard -> System.out.println(
                        converter.convertQuestionCardToString(questionCard)));
    }
}
