package com.app.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicatePlayerException extends RuntimeException{
    public DuplicatePlayerException(String message) {
        super(message);
    }
}
