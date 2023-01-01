package ru.otus.gorenkov.service;

import org.springframework.stereotype.Component;
import ru.otus.gorenkov.domain.Answer;
import ru.otus.gorenkov.domain.QuestionCard;
import ru.otus.gorenkov.exception.CorrectAnswerNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionConverterImpl implements QuestionConverter{

    @Override
    public List<QuestionCard> convertRawDataToQuestionCards(List<String[]> allLines) {
        return allLines.stream()
                .filter(el -> el != null)
                .map(el -> buildQuestionCard(el))
                .collect(Collectors.toList());
    }

    @Override
    public String convertQuestionCardToString(QuestionCard questionCard) {
        final String[] res = {questionCard.getQuestion() + "? \n"};
        questionCard.getAnswers().forEach(a -> res[0] += (a.getIdWithDelimiter() + a.getText()) + "\n");
        return res[0];
    }

    private QuestionCard buildQuestionCard(String[] line) {
        String questText = line[0];
        QuestionCard questCard = new QuestionCard(questText);

        String[] answers = Arrays.copyOfRange(line, 1, line.length-1);
        List<Answer> answerList = new ArrayList<>();
        char firstLetter = 'a';
        for(int i = 0; i < answers.length ; i++){
            char charID = (char)(firstLetter + i);
            String text = answers[i];
            Answer answer = new Answer(text);
            answer.setId(String.valueOf(charID));
            answer.setCorrect(false);
            answerList.add(answer);
        }
        questCard.setAnswers(answerList);

        String correctId = line[line.length-1];
        List<Answer> findCorrectAnswer = answerList.stream().filter(answer -> answer.getId().equals(correctId))
                .collect(Collectors.toList());
        if (findCorrectAnswer.size() == 0) {
            throw new CorrectAnswerNotFoundException(String.format("Not found correct answer for question %s", questText));
        }
        findCorrectAnswer.get(0).setCorrect(true);

        return questCard;
    }
}
