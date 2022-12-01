package ru.otus.gorenkov.service;

import ru.otus.gorenkov.dao.QuestionCardDao;
import ru.otus.gorenkov.domain.QuestionCard;

import java.util.List;

public class QuestionServiceImpl implements QuestionService{

    private final QuestionCardDao questionDao;

    public QuestionServiceImpl(QuestionCardDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<QuestionCard> getAllQuestionCards() {
        return questionDao.getAllQuestionCards();
    }
}
