package com.app.tictactoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class PlayerNotActiveException extends RuntimeException{
    public PlayerNotActiveException(String message){
        super(message);
    }
}
