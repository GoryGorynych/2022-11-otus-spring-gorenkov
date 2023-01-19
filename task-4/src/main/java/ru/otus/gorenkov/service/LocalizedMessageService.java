package ru.otus.gorenkov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.gorenkov.config.LocaleProps;

@RequiredArgsConstructor
@Service
public class LocalizedMessageService implements MessageServise {

    private final LocaleProps localeProps;
    private final MessageSource messageSource;

    @Override
    public String getMessage(String code, Object[] params) {
        return messageSource.getMessage(code, params, localeProps.getLocale());
    }

    @Override
    public String getMessage(String code) {
        return getMessage(code, null);
    }
}
