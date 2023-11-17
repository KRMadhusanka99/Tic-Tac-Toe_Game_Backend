package com.app.tictactoe.controller;

import com.app.tictactoe.dto.RequestDTO.GameRequestStartDTO;
import com.app.tictactoe.dto.RequestDTO.PlayerRequestTurnDTO;
import com.app.tictactoe.dto.ResponseDTO.GameResponseDTO;
import com.app.tictactoe.exception.*;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    private Player player;

    @PostMapping("/start")
    public ResponseEntity<GameResponseDTO> startGame(@RequestBody GameRequestStartDTO gameRequestStartDTO){
        try {
            player = gameService.startGame(gameRequestStartDTO);
            return ResponseEntity.ok(new GameResponseDTO(player.getPlayerName(),"Game start successfully"));
        }catch (PlayerNotExistException | PlayerActiveException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new GameResponseDTO(gameRequestStartDTO.getPlayerName(),e.getMessage()));
        }
    }

    @PatchMapping({"/reset","/reset/{playerName}"}) // Array of End points with not required playerName
    public ResponseEntity<GameResponseDTO> resetGame(@PathVariable(required = false) String playerName){
        try {
            player = gameService.resetGame(playerName);
            return ResponseEntity.ok().body(new GameResponseDTO(playerName,"Game rest successfully"));
        }catch (PlayerNotExistException | PlayerNotActiveException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new GameResponseDTO(playerName,e.getMessage()));
        }
    }

    @PostMapping("/playerTurn")
    public ResponseEntity<GameResponseDTO> playerTurn(@RequestBody PlayerRequestTurnDTO playerRequestTurnDTO){
        try {
            player = gameService.playerTurn(playerRequestTurnDTO);
            return ResponseEntity.ok(new GameResponseDTO(playerRequestTurnDTO.getPlayerName(),"X placed successfully"));
        }catch (PlayerNotExistException | PositionNotAvailableException| PlayerNotActiveException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new GameResponseDTO(playerRequestTurnDTO.getPlayerName(),e.getMessage()));
        }catch (InvalidInputException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new GameResponseDTO(playerRequestTurnDTO.getPlayerName(),e.getMessage()));
        }catch (GameOverException e){
            return ResponseEntity.status(HttpStatus.OK).body(new GameResponseDTO(playerRequestTurnDTO.getPlayerName(),e.getMessage()));
        }
    }
}
