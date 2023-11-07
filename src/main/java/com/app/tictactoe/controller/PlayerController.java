package com.app.tictactoe.controller;

import com.app.tictactoe.exception.DuplicatePlayerException;
import com.app.tictactoe.exception.InvalidInputException;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/register")
    public ResponseEntity<String> addPlayer(@RequestBody Player player){
        try {
            Player savedPlayer = playerService.addPlayer(player);
            return ResponseEntity.ok("Player "+savedPlayer.getPlayerName()+" is successfully registered.");
        } catch (DuplicatePlayerException | InvalidInputException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
