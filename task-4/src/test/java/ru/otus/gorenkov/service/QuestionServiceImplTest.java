package ru.otus.gorenkov.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.gorenkov.dao.QuestionCardDao;
import ru.otus.gorenkov.domain.Answer;
import ru.otus.gorenkov.domain.QuestionCard;
import ru.otus.gorenkov.exception.CorrectAnswerNotFoundException;
import ru.otus.gorenkov.shell.WelcomeApplicationRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Сервис карточек вопросов ")
@SpringBootTest
class QuestionServiceImplTest {

    @MockBean
    private QuestionCardDao questionDao;

//    @MockBean
//    private QuestionConverter converter;

    @MockBean
    private WelcomeApplicationRunner runner;

    @Autowired
    private QuestionServiceImpl questionService;

    private static List<QuestionCard> testQuestionCards;

    @BeforeAll
    public static void createTestQuestionCardList() {
        List<String[]> questions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String[] questionAndAnswer = {"Вопрос " + i, "Ответ 1", "Ответ 2", "Ответ 3", "Ответ 4", "a"};
            questions.add(questionAndAnswer);
        }

        testQuestionCards = new ArrayList<>();
        for (String[] quest : questions) {
            QuestionCard questionCard = new QuestionCard(quest[0]);
            List<Answer> answerList = new ArrayList<>();
            String[] answers = Arrays.copyOfRange(quest, 1, quest.length-1);
            for (int i = 0; i < answers.length; i++) {
                Answer answer = new Answer(answers[i]);
                answerList.add(answer);
            }
            questionCard.setAnswers(answerList);
            testQuestionCards.add(questionCard);
        }
    }

    @DisplayName("должен возвращать эквивалентный список вопросов")
    @Test
    void shouldReturnEqualQuestionCardList() {

        List<QuestionCard> cloneQuestionCards = new ArrayList<>(testQuestionCards);

        given(questionDao.getAll()).willReturn(cloneQuestionCards);
        var actualQuestionCards = questionService.getAllQuestionCards();

        verify(questionDao, times(1)).getAll();
        assertThat(actualQuestionCards).hasSameElementsAs(testQuestionCards);

    }

    @DisplayName("должен корректно обрабатывать исключение CorrectAnswerNotFoundException")
    @Test
    void shouldCatchCorrectAnswerNotFoundException() {

        given(questionDao.getAll()).willThrow(CorrectAnswerNotFoundException.class);
        var actualQuestionCards = questionService.getAllQuestionCards();

        verify(questionDao, times(1)).getAll();
        assertThat(actualQuestionCards).isNotNull();

    }
}