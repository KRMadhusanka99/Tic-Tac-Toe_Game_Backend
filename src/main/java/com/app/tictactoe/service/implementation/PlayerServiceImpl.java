package com.app.tictactoe.service.implementation;

import com.app.tictactoe.dto.RequestDTO.PlayerRequestRegisterDTO;
import com.app.tictactoe.exception.DuplicatePlayerException;
import com.app.tictactoe.exception.InvalidInputException;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.repository.PlayerRepository;
import com.app.tictactoe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired //inject
    private PlayerRepository playerRepository;

    @Override
    public Player addPlayer(PlayerRequestRegisterDTO playerRequestRegisterDTO) {
        // Check if playerName already exists
        Player existingPlayer = playerRepository.findByPlayerName(playerRequestRegisterDTO.getPlayerName());

        if (existingPlayer != null) {
            // Handle duplicate player name (throw exception or return null, etc.)
            throw new DuplicatePlayerException("Player with this name "+playerRequestRegisterDTO.getPlayerName()+" is already exists.");
        }

        //Check legal Arguments
        if(playerRequestRegisterDTO.getPlayerName() == null || playerRequestRegisterDTO.getPlayerName().isEmpty() ||
                playerRequestRegisterDTO.getPassword() == null || playerRequestRegisterDTO.getPassword().isEmpty()) {
            throw new InvalidInputException("Player name and password cannot be empty");
        }

        Player newPlayer = new Player(
                playerRequestRegisterDTO.getPlayerName(),
                playerRequestRegisterDTO.getPassword()
        );

        Player savedPlayer = playerRepository.save(newPlayer);
        return savedPlayer;
    }
}
