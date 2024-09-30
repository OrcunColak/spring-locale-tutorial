package com.colak.springlocaletutorial.controller;

import com.colak.springlocaletutorial.util.MessageUtil;
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
}
