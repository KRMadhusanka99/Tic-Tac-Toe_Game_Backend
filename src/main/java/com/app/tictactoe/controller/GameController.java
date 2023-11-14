package com.app.tictactoe.controller;

import com.app.tictactoe.dto.RequestDTO.GameRequestStartDTO;
import com.app.tictactoe.dto.ResponseDTO.GameResponseDTO;
import com.app.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private GameService gameService;

    @PostMapping("/strat")
    public ResponseEntity<GameResponseDTO> startGame(@RequestBody GameRequestStartDTO gameRequestStartDTO){
        return ResponseEntity.ok(new GameResponseDTO("game start successfully"));
    }
}
