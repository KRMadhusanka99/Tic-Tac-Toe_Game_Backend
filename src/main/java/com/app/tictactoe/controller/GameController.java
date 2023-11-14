package com.app.tictactoe.controller;

import com.app.tictactoe.dto.RequestDTO.GameRequestStartDTO;
import com.app.tictactoe.dto.ResponseDTO.GameResponseDTO;
import com.app.tictactoe.exception.PlayerActiveException;
import com.app.tictactoe.exception.PlayerNotExistException;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<GameResponseDTO> startGame(@RequestBody GameRequestStartDTO gameRequestStartDTO){
        try {
            Player player = gameService.startGame(gameRequestStartDTO);
            return ResponseEntity.ok(new GameResponseDTO("Game start successfully for " + player.getPlayerName()));
        }catch (PlayerNotExistException | PlayerActiveException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new GameResponseDTO(e.getMessage()));
        }
    }
}
