package com.app.tictactoe.service;

import com.app.tictactoe.dto.RequestDTO.GameRequestStartDTO;
import com.app.tictactoe.model.Player;

public interface GameService {
    Player startGame(GameRequestStartDTO gameRequestStartDTO);
    Player resetGame(String playerName);
}
