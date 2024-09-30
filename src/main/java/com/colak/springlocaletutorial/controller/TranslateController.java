package com.colak.springlocaletutorial.controller;

import com.colak.springlocaletutorial.util.MessageUtil;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleRecordNotFoundException(Exception exception) {

        return ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getLocalizedMessage());
    }
}
