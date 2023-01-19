package ru.otus.gorenkov.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.InteractiveShellRunner;
import org.springframework.stereotype.Component;
import ru.otus.gorenkov.service.IOService;

@RequiredArgsConstructor
@Component
@Order(InteractiveShellRunner.PRECEDENCE - 100)
public class WelcomeApplicationRunner implements ApplicationRunner {
    private final IOService ioService;

    @Override
    public void run(ApplicationArguments args) {
        ioService.out("survey.welcome.message", null);
    }

}
