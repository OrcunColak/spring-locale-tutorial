package com.colak.springlocaletutorial.controller;

import com.colak.springlocaletutorial.util.MessageUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/translate")
public class TranslateController {

    @GetMapping(path = "/translate/{key}")
    public String translate(@PathVariable("key") String key) {
        return MessageUtil.getMessage(key);
    }

    @GetMapping(path = "/error")
    public void getNotFoundError() {
        var message = MessageUtil.getMessage("error.entity.not.found");
        throw new RuntimeException(message);
    }

    @PostMapping(path = "/user")
    public UserRequest postUser(@Valid @RequestBody UserRequest userRequest) {
        return userRequest;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleRecordNotFoundException(Exception exception) {
        return ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getLocalizedMessage());
    }
}
