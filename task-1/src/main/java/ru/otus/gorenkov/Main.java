package ru.otus.gorenkov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.gorenkov.service.QuestionService;

public class Main {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean("questionService", QuestionService.class);

        service.getAllQuestionCards().forEach(System.out::println);
    }
}