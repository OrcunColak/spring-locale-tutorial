package com.colak.springlocaletutorial.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
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

    @ParameterizedTest
    @CsvSource(
            {
                    " ,Entity not found!",
                    "?language=fr,Entité non trouvée!",
                    "?language=tr,Kayıt bulunamadı!",
                    "?language=en,Entity not found!",

            }
    )
    void translateException(String input, String expected) {
        String getUrl = "/api/translate/error" + Objects.toString(input, "");

        ResponseEntity<ProblemDetail> response = testRestTemplate.getForEntity(
                getUrl,
                ProblemDetail.class);
        ProblemDetail body = response.getBody();
        String detail = body.getDetail();
        assertEquals(expected, detail);
    }

    @ParameterizedTest
    @CsvSource(
            {
                    " ,[\"The surname field must be between 5 and 10 characters!\"]",
                    "?language=fr,[\"Le champ du nom de famille doit comporter entre 5 et 10 caractères!\"]",
                    "?language=tr,[\"Soyisim alanı en az 5 en fazla 10 karakter olabilir!\"]",
                    "?language=en,[\"The surname field must be between 5 and 10 characters!\"]",

            }
    )
    void translateValidation(String input, String expected) {
        String postUrl = "/api/translate/user" + Objects.toString(input, "");

        // Create a UserRequest object with an invalid surname (less than 5 characters)
        UserRequest invalidUserRequest = new UserRequest();
        invalidUserRequest.setSurname("Ana"); // This should fail validation

        // Create an HTTP entity with the valid request
        HttpEntity<UserRequest> requestEntity = new HttpEntity<>(invalidUserRequest);

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                postUrl,
                requestEntity,
                String.class);
        // Verify the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);  // Expect a bad request status

        assertEquals(expected, response.getBody());


    }

}
