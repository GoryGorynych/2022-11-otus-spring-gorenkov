package ru.otus.gorenkov.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.gorenkov.config.LocaleProps;
import ru.otus.gorenkov.dao.QuestionCardDaoCsv;
import ru.otus.gorenkov.shell.WelcomeApplicationRunner;

import java.util.Locale;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис локализации сообщений ")
@SpringBootTest
class LocalizedMessageServiceTest {

    @MockBean
    private LocaleProps localeProps;
    @MockBean
    private QuestionCardDaoCsv questionCardDaoCsv;
    @MockBean
    private SurveyProcessorStudent surveyProcessorStudent;
    @MockBean
    private WelcomeApplicationRunner runner;

    @Autowired
    public MessageSource messageSource;
    @Autowired
    private MessageServise messageServise;

    @DisplayName("должен возвращать правильное сообщение без параметров в зависимости от локали")
    @ParameterizedTest
    @MethodSource("localeMessageArguments")
    void shouldReturnCorrectNotParametersMessageByLocale(String locale, String expectedMessage) {
        given(localeProps.getLocale()).willReturn(new Locale(locale));
        assertThat(messageServise.getMessage("survey.input.firstname")).contains(expectedMessage);
    }

    private static Stream<Arguments> localeMessageArguments() {
        return Stream.of(
                Arguments.of("ru", "Введите ваше имя..."),
                Arguments.of("en", "Enter your first name...")
        );
    }

}