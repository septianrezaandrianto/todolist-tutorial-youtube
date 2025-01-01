package com.app.todolist_be.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataExistException extends RuntimeException {

    public DataExistException(String message) {
        super(message);
    }

}
