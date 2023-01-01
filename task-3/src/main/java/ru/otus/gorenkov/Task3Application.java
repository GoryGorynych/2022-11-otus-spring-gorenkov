package ru.otus.gorenkov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.gorenkov.service.SurveyProcessor;

@SpringBootApplication
public class Task3Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Task3Application.class, args);
        SurveyProcessor surveyProcessor = context.getBean(SurveyProcessor.class);
        surveyProcessor.runProcess();
    }

}
