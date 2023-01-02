package ru.otus.gorenkov.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "appication")
public class AppProps implements GeneralProps, LocaleProps {

    private String pathCsv;

    private int passNumberOfCorrectAnswer;
    private Locale locale;

    @Override
    public String getPathCsv() {
        return pathCsv;
    }

    public void setPathCsv(String pathCsv) {
        this.pathCsv = pathCsv;
    }

    @Override
    public int getPassNumberOfCorrectAnswer() {
        return passNumberOfCorrectAnswer;
    }

    public void setPassNumberOfCorrectAnswer(int passNumberOfCorrectAnswer) {
        this.passNumberOfCorrectAnswer = passNumberOfCorrectAnswer;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
