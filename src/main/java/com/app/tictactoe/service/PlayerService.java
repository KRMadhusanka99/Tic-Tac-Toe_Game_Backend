package com.app.tictactoe.service;

import com.app.tictactoe.dto.RequestDTO.PlayerRequestRegisterDTO;
import com.app.tictactoe.model.Player;

public interface PlayerService {
    Player addPlayer (PlayerRequestRegisterDTO playerRequestRegisterDTO);
}
