package com.app.tictactoe.service;

import com.app.tictactoe.dto.RequestDTO.GameRequestStartDTO;
import com.app.tictactoe.dto.RequestDTO.PlayerRequestTurnDTO;
import com.app.tictactoe.model.Player;

public interface GameService {
    String startGame(GameRequestStartDTO gameRequestStartDTO);
    String resetGame(String playerName);
    Player playerTurn(PlayerRequestTurnDTO playerRequestTurnDTO);
}
