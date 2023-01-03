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
    private final MessageServise messageServise;

    public ConcoleIOService(MessageServise messageServise, MessageSource source, LocaleProps config) {
        out = System.out;
        scanner = new Scanner(System.in);
        this.messageServise = messageServise;
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
        out.println(messageServise.getMessage(messageCode, params));
    }
}
