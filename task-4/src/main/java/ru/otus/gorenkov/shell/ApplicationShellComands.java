package ru.otus.gorenkov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.gorenkov.service.MessageServise;
import ru.otus.gorenkov.service.SurveyProcessor;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellComands {

    boolean agreeRules;
    private final SurveyProcessor surveyProcessor;
    private final MessageServise messageServise;

    @ShellMethod(value = "Agree with rules", key = {"yes", "y"})
    private String confirm() {
        agreeRules = true;
        return messageServise.getMessage("survey.shell.start");
    }

    @ShellMethod(value = "Refuse rules", key = {"no", "n"})
    private String refuse() {
        agreeRules = false;
        return messageServise.getMessage("survey.shell.refuse");
    }

    @ShellMethod(value = "Start survey", key = {"start", "s"})
    @ShellMethodAvailability(value = "isStartCommandAvailable")
    private String start(@ShellOption(defaultValue = "user") String key) {
        surveyProcessor.runProcess();
        return messageServise.getMessage("survey.shell.complete");
    }

    private Availability isStartCommandAvailable() {
        return agreeRules ? Availability.available() :
                Availability.unavailable(messageServise.getMessage("survey.shell.start.unvailable"));
    }


}
