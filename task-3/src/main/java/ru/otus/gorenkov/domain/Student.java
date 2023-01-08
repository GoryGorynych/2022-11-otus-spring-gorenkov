package ru.otus.gorenkov.domain;

public class Student {

    private String firstName;
    private String lastName;
    private int correctAnswerCount;

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void incrementCorrectAnswer() {
        this.correctAnswerCount++;
    }

    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
