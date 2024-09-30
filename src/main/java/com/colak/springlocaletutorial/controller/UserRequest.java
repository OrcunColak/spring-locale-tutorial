package com.colak.springlocaletutorial.controller;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @Size(message = "{error.validation.invalid.size.surname}", min = 5, max = 10)
    private String surname;
}
