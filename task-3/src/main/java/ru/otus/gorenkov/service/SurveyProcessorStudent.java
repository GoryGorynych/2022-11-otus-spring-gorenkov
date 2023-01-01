package ru.otus.gorenkov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.gorenkov.config.AppProps;
import ru.otus.gorenkov.domain.QuestionCard;
import ru.otus.gorenkov.domain.Student;

import java.util.List;
import java.util.Scanner;

@Component
public class SurveyProcessorStudent implements SurveyProcessor {

    private Student student;
    private final QuestionService questionService;
    private final QuestionConverter converter;
    private final AppProps appProps;
    private final MessageSource messageSource;
    private Scanner scanner = new Scanner(System.in);


    private static final String RESULT_MESSAGE = "Student %s %s the test. Correct answer: %d";

    public SurveyProcessorStudent(MessageSource messageSource, QuestionService questionService, QuestionConverter converter, AppProps props) {
        this.messageSource = messageSource;
        this.questionService = questionService;
        this.converter = converter;
        this.appProps = props;
    }

    @Override
    public void runProcess() {
        askPersonalData();
        askQuestions();
        printResult();
    }

    @Override
    public void askPersonalData() {

        System.out.println(messageSource.getMessage("survey.input.firstname", null, appProps.getLocale()));
        String firstName = scanner.nextLine();

        System.out.println(messageSource.getMessage("survey.input.lastname", null, appProps.getLocale()));
        String lastName = scanner.nextLine();

        student = new Student(firstName, lastName);
    }

    @Override
    public void askQuestions() {
        List<QuestionCard> allQuestionCards = questionService.getAllQuestionCards();
        System.out.println("\n" + messageSource.getMessage("survey.tooltip.begin", null, appProps.getLocale()) + "\n");
        for (QuestionCard questCard : allQuestionCards) {
            System.out.println(converter.convertQuestionCardToString(questCard));
            String inputAnswerId = scanner.nextLine();
            if (questCard.getAnswers().stream().anyMatch(answer ->
                    answer.getId().equals(inputAnswerId) && answer.isCorrect())) {
                student.incrementCorrectAnswer();
            }
        }
    }

    @Override
    public void printResult() {
        if (student.getCorrectAnswerCount() >= appProps.getPassNumberOfCorrectAnswer()) {
            System.out.println(messageSource.getMessage("survey.output.result.success", new Object[]{student,
                    student.getCorrectAnswerCount()}, appProps.getLocale()));
        } else {
            System.out.println(messageSource.getMessage("survey.output.result.failed", new Object[]{student,
                    student.getCorrectAnswerCount()}, appProps.getLocale()));
        }
    }
}
