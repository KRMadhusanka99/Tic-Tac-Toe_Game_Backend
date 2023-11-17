package com.app.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.OK)
public class GameOverException extends RuntimeException{
    public GameOverException(String message){
        super(message);
    }
}
