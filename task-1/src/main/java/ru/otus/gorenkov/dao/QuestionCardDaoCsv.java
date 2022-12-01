package ru.otus.gorenkov.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.gorenkov.domain.Answer;
import ru.otus.gorenkov.domain.QuestionCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionCardDaoCsv implements QuestionCardDao {

    private final Resource csvResource;

    public QuestionCardDaoCsv(String path) {
        csvResource = new ClassPathResource(path);
    }
    @Override
    public List<QuestionCard> getAllQuestionCards() {

        List<String[]> allLines = readCsv();

        List<QuestionCard> allQuest = allLines.stream()
                .filter(el -> el != null)
                .map(el -> buildQuestionCard(el))
                .collect(Collectors.toList());

        return allQuest;
    }

    @Override
    public QuestionCard getQuestionCardById(int id) {
        return null;
    }

    private QuestionCard buildQuestionCard(String[] line) {

        QuestionCard questCard = new QuestionCard();
        questCard.setQuestion(line[0]);

        String[] answers = Arrays.copyOfRange(line, 1, line.length);
        List<Answer> answerList = new ArrayList<>();
        char firstLetter = 'a';
        for(int i = 0; i < answers.length ; i++){
            Answer answer = new Answer();
            char charID = (char)(firstLetter + i);
            answer.setId(String.valueOf(charID));
            answer.setText(answers[i]);
            answer.setRight(false);
            answerList.add(answer);
        }
        questCard.setAnswers(answerList);
        return questCard;
    }

    private List<String[]> readCsv() {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        List<String[]> allLines = null;
        try(CSVReader reader = new CSVReaderBuilder(
                new FileReader(csvResource.getFile()))
                .withCSVParser(csvParser)
                .withSkipLines(1)
                .build()){
            allLines = reader.readAll();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return allLines;
    }

}
