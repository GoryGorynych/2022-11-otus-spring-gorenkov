package ru.otus.gorenkov.dao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.gorenkov.config.AppProps;
import ru.otus.gorenkov.domain.QuestionCard;
import ru.otus.gorenkov.service.QuestionConverter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class QuestionCardDaoCsv implements QuestionCardDao {
    private final Resource csvResource;
    private final QuestionConverter converter;
    private final AppProps appProps;

    public QuestionCardDaoCsv(AppProps props, QuestionConverter converter) {

        this.appProps = props;
        csvResource = new ClassPathResource(appProps.getPathCsv());
        this.converter = converter;
    }
    @Override
    public List<QuestionCard> getAll() {
        List<String[]> allLines = readCsv();
        return converter.convertRawDataToQuestionCards(allLines);
    }

    @Override
    public QuestionCard getQuestionCardById(int id) {
        return null;
    }

    private List<String[]> readCsv() {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(
                new FileReader(csvResource.getFile()))
                .withCSVParser(csvParser)
                .withSkipLines(1)
                .build()) {
            return reader.readAll();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

}
