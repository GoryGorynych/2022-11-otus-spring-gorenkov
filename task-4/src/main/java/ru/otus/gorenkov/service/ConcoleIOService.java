package ru.otus.gorenkov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.gorenkov.config.LocaleProps;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConcoleIOService implements IOService {

    private final PrintStream out;
    private final Scanner scanner;

    private final MessageSource messageSource;
    private final LocaleProps localeProps;

    public ConcoleIOService(MessageSource source, LocaleProps config) {
        out = System.out;
        scanner = new Scanner(System.in);
        this.messageSource = source;
        this.localeProps = config;
    }

    @Override
    public void out(String messgae) {
        out.println(messgae);
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public void out(String messageCode, Object[] params) {
        out.println(messageSource.getMessage(messageCode, params, localeProps.getLocale()));
    }
}
