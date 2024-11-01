package com.colak.springtutorial.util;

import com.colak.springtutorial.config.MessageSourceConfig;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

public class MessageUtil {

    public static String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        MessageSource messageSource = MessageSourceConfig.reloadableResourceBundleMessageSource();
        return messageSource.getMessage(key, null, locale);
    }
}
