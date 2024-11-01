package com.colak.springtutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

/**
 * MessageSource is used for resolving messages, with support for the parameterization and internationalization of the messages.
 * Spring contains two built-in MessageSource implementations:
 * ResourceBundleMessageSource and ReloadableResourceBundleMessageSource.
 * <p>
 * The latter is able to reload message definitions without restarting the Virtual Machine.
 */
@Configuration
public class MessageSourceConfig {

    /**
     * The ResourceBundleMessageSource uses underlying JDK's ResourceBundle implementation.
     * The java.util.ResourceBundle loads locale specific files or bundles only once.
     * It caches loaded bundles forever: Reloading a bundle during program execution is not possible.
     * <p>
     * See <a href="https://zetcode.com/spring/messagesource/">explanation</a>
     */
    @Bean
    ResourceBundleMessageSource resourceBundleMessageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("lang/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }

    /**
     * See <a href="https://learningviacode.blogspot.com/2012/07/reloadable-messagesources.html">explanation</a>
     */
    @Bean
    @Primary
    public static ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:lang/messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setCacheSeconds(1);

        return messageSource;
    }
}
