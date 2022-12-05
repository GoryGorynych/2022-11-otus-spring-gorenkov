package ru.otus.gorenkov.domain;

public class Answer {

    private static final String DELIMITER_ID = ")";
    private String id;
    private final String text;
    private boolean right;

    public Answer(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public String getIdWithDelimiter() {
        return id + DELIMITER_ID;
    }
}
