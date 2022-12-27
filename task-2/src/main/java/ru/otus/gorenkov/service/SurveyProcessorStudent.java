package ru.otus.gorenkov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.gorenkov.domain.QuestionCard;
import ru.otus.gorenkov.domain.Student;

import java.util.List;
import java.util.Scanner;

@Component
public class SurveyProcessorStudent implements SurveyProcessor {

    private Student student;
    private final QuestionService questionService;
    private final QuestionConverter converter;
    private Scanner scanner = new Scanner(System.in);

    private static final String RESULT_MESSAGE = "Student %s %s the test. Correct answer: %d";

    @Value("${survey.passNumberOfCorrectAnswer}")
    private int passNumberOfCorrectAnswer;

    public SurveyProcessorStudent(QuestionService questionService, QuestionConverter converter) {
        this.questionService = questionService;
        this.converter = converter;
    }

    @Override
    public void runProcess() {
        askPersonalData();
        askQuestions();
        printResult();
    }

    @Override
    public void askPersonalData() {

        System.out.println("Enter your first name...");
        String firstName = scanner.nextLine();

        System.out.println("Enter your last name...");
        String lastName = scanner.nextLine();

        student = new Student(firstName, lastName);
    }

    @Override
    public void askQuestions() {
        List<QuestionCard> allQuestionCards = questionService.getAllQuestionCards();
        System.out.println("\n Choose one correct answer... \n");
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
        if (student.getCorrectAnswerCount() >= passNumberOfCorrectAnswer) {
            System.out.println(String.format(RESULT_MESSAGE, student, "passed", student.getCorrectAnswerCount()));
        } else {
            System.out.println(String.format(RESULT_MESSAGE, student, "failed", student.getCorrectAnswerCount()));
        }
    }
}
