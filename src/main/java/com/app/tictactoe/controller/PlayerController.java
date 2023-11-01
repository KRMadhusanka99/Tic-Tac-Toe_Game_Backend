package com.app.tictactoe.controller;

import com.app.tictactoe.model.Player;
import com.app.tictactoe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String addPlayer(@RequestBody Player player){
        return playerService.addPlayer(player);
    }
}
