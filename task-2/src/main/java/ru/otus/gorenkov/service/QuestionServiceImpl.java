package ru.otus.gorenkov.service;

import org.springframework.stereotype.Service;
import ru.otus.gorenkov.dao.QuestionCardDao;
import ru.otus.gorenkov.domain.QuestionCard;
import ru.otus.gorenkov.exception.CorrectAnswerNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    private final QuestionCardDao questionDao;
    private final QuestionConverter converter;
    public QuestionServiceImpl(QuestionCardDao questionDao, QuestionConverter converter) {
        this.questionDao = questionDao;
        this.converter = converter;
    }

    @Override
    public List<QuestionCard> getAllQuestionCards() {
        List<QuestionCard> resultQuestionList = null;
        try {
            resultQuestionList = questionDao.getAll();
        } catch (CorrectAnswerNotFoundException notFoundException) {
            if (resultQuestionList == null) {
                resultQuestionList = new ArrayList<>();
            }
            System.out.println(notFoundException);
        }
        return resultQuestionList;
    }

    @Override
    public void printAllQuestionCards() {
        getAllQuestionCards()
                .forEach(questionCard -> System.out.println(
                        converter.convertQuestionCardToString(questionCard)));
    }
}
