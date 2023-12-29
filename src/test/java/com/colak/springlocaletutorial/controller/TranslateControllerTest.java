package com.colak.springlocaletutorial.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TranslateControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    // This test was valid when we were using AcceptHeaderLocaleResolver
    /*
    @ParameterizedTest
    @CsvSource(
            {
                    "fr,Bonjour!",
                    "tr,Merhaba!",
                    "en,Hello!",

            }
    )
    void translateWithHttpHeader(String input, String expected) {
        String getUrl = "/api/translate/translate/greeting";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", input);

        ResponseEntity<String> response = testRestTemplate.exchange(
                getUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class);
        String body = response.getBody();
        assertEquals(expected, body);
    }
    */

    @ParameterizedTest
    @CsvSource(
            {
                    " ,Hello!",
                    "?language=fr,Bonjour!",
                    "?language=tr,Merhaba!",
                    "?language=en,Hello!",

            }
    )
    void translateWithPathVariable(String input, String expected) {
        String getUrl = "/api/translate/translate/greeting" + Objects.toString(input, "");


        ResponseEntity<String> response = testRestTemplate.getForEntity(
                getUrl,
                String.class);
        String body = response.getBody();
        assertEquals(expected, body);
    }

}
