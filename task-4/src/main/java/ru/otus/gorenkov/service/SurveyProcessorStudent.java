package ru.otus.gorenkov.service;

import org.springframework.stereotype.Component;
import ru.otus.gorenkov.config.GeneralProps;
import ru.otus.gorenkov.domain.QuestionCard;
import ru.otus.gorenkov.domain.Student;

import java.util.List;

@Component
public class SurveyProcessorStudent implements SurveyProcessor {

    private Student student;
    private final QuestionService questionService;
    private final QuestionConverter converter;
    private final GeneralProps generalProps;
    private final IOService ioService;

    public SurveyProcessorStudent(QuestionService questionService, QuestionConverter converter,
                                  GeneralProps generalProps, IOService ioService) {
        this.questionService = questionService;
        this.converter = converter;
        this.generalProps = generalProps;
        this.ioService = ioService;
    }

    @Override
    public void runProcess() {
        askPersonalData();
        askQuestions();
        printResult();
    }

    @Override
    public void askPersonalData() {

        ioService.out("survey.input.firstname", null);
        String firstName = ioService.readString();

        ioService.out("survey.input.lastname", null);
        String lastName = ioService.readString();

        student = new Student(firstName, lastName);
    }

    @Override
    public void askQuestions() {

        List<QuestionCard> allQuestionCards = questionService.getAllQuestionCards();
        ioService.out("survey.tooltip.begin", null);
        ioService.out("");

        for (QuestionCard questCard : allQuestionCards) {
            ioService.out(converter.convertQuestionCardToString(questCard));
            String inputAnswerId = ioService.readString();
            if (questCard.getAnswers().stream().anyMatch(answer ->
                    answer.getId().equals(inputAnswerId) && answer.isCorrect())) {
                student.incrementCorrectAnswer();
            }
        }
    }

    @Override
    public void printResult() {
        if (student.getCorrectAnswerCount() >= generalProps.getPassNumberOfCorrectAnswer()) {
            ioService.out("survey.output.result.success", new Object[]{student, student.getCorrectAnswerCount()});
        } else {
            ioService.out("survey.output.result.failed", new Object[]{student, student.getCorrectAnswerCount()});
        }
    }
}
