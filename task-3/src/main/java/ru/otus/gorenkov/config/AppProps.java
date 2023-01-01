package ru.otus.gorenkov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "appication")
public class AppProps {

    private String pathCsv;

    private int passNumberOfCorrectAnswer;
    private Locale locale;

    public String getPathCsv() {
        return pathCsv;
    }

    public void setPathCsv(String pathCsv) {
        this.pathCsv = pathCsv;
    }

    public int getPassNumberOfCorrectAnswer() {
        return passNumberOfCorrectAnswer;
    }

    public void setPassNumberOfCorrectAnswer(int passNumberOfCorrectAnswer) {
        this.passNumberOfCorrectAnswer = passNumberOfCorrectAnswer;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
