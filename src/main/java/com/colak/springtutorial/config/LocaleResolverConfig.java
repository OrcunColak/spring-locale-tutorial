package com.colak.springtutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleResolverConfig implements WebMvcConfigurer {

    // switch to a new locale based on the value of the "language" parameter when present on the request path
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        // The LocaleResolver interface has implementations that determine the current locale based on
        // the session, cookies, the Accept-Language header, or a fixed value.

        // FixedLocaleResolver :
        // always resolves the locale to a singular fixed language mentioned in the project properties.
        // Mostly used for debugging purposes.

        // AcceptHeaderLocaleResolver :
        // resolves the locale using an “accept-language” HTTP header retrieved from an HTTP request.

        // SessionLocaleResolver  :
        // resolves the locale and stores it in the HttpSession of the user.
        // But as you might have wondered, yes, the resolved locale data is persisted only for as long as the session is live.

        // CookieLocaleResolver  :
        // resolves the locale and stores it in a cookie stored on the user’s machine.
        // Now, as long as browser cookies aren’t cleared by the user,
        // once resolved the resolved locale data will last even between sessions. Cookies save the day!

//        AcceptHeaderLocaleResolver slr = new AcceptHeaderLocaleResolver();
//        slr.setDefaultLocale(Locale.US);
//        return slr;

        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        slr.setLocaleAttributeName("current.locale");
        slr.setTimeZoneAttributeName("current.timezone");
        return slr;
    }
}
