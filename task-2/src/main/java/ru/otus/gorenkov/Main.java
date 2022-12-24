package ru.otus.gorenkov;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.gorenkov.service.SurveyProcessor;

@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        SurveyProcessor surveyProcessor = context.getBean(SurveyProcessor.class);

        surveyProcessor.runProcess();
    }
}