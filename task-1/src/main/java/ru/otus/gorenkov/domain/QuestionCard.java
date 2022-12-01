package ru.otus.gorenkov.domain;

import java.util.List;

public class QuestionCard {

    private String id;
    private String question;
    private List<Answer> answers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        final String[] res = {question + "? \n"};
        answers.forEach(a -> res[0] += (a.getIdWithDelimiter() + a.getText()) + "\n");
        return res[0];
    }
}
