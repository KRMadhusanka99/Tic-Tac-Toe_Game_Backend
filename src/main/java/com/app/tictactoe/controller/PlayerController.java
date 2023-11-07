package com.app.tictactoe.controller;

import com.app.tictactoe.dto.RequestDTO.PlayerRequestRegisterDTO;
import com.app.tictactoe.dto.ResponseDTO.PlayerResponseDTO;
import com.app.tictactoe.exception.DuplicatePlayerException;
import com.app.tictactoe.exception.InvalidInputException;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.service.PlayerService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PlayerResponseDTO> addPlayer(@RequestBody PlayerRequestRegisterDTO playerRequestRegisterDTO){
        try {
            Player savedPlayer = playerService.addPlayer(playerRequestRegisterDTO);
            return ResponseEntity.ok(new PlayerResponseDTO("Player "+savedPlayer.getPlayerName()+ " is successfully registered."));
        } catch (DuplicatePlayerException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new PlayerResponseDTO(e.getMessage()));
        } catch (InvalidInputException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PlayerResponseDTO(e.getMessage()));
        }
    }
}
