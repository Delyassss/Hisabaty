package com.hisabaty.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValueAlreadyExist extends RuntimeException {
    public ValueAlreadyExist(String message) {
        super(message);
    }
}
    