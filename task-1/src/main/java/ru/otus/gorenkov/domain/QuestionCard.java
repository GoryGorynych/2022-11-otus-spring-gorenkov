package ru.otus.gorenkov.domain;

import java.util.List;

public class QuestionCard {

    private String id;
    private final String question;
    private List<Answer> answers;

    public QuestionCard(String question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}
