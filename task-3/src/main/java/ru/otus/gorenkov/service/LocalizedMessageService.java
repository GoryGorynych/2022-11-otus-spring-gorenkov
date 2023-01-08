package ru.otus.gorenkov.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.gorenkov.config.LocaleProps;

@Service
public class LocalizedMessageService implements MessageServise {

    private final LocaleProps localeProps;
    private final MessageSource messageSource;

    public LocalizedMessageService(LocaleProps localeProps, MessageSource messageSource) {
        this.localeProps = localeProps;
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String code, Object[] params) {
        return messageSource.getMessage(code, params, localeProps.getLocale());
    }

    @Override
    public String getMessage(String code) {
        return getMessage(code, null);
    }
}
