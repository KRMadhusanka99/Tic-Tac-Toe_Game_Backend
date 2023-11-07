package com.app.tictactoe.service.implementation;

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
    public Player addPlayer(Player player) {
        // Check if playerName already exists
        Player existingPlayer = playerRepository.findByPlayerName(player.getPlayerName());

        if (existingPlayer != null) {
            // Handle duplicate player name (throw exception or return null, etc.)
            throw new DuplicatePlayerException("Player with this name "+player.getPlayerName()+" is already exists.");
        }

        //Check legal Arguments
        if(player.getPlayerName() == null || player.getPlayerName().isEmpty() ||
                player.getPassword() == null || player.getPassword().isEmpty()) {
            throw new InvalidInputException("Player name and password cannot be empty");
        }

        Player newPlayer = new Player();
        newPlayer.setPlayerName(player.getPlayerName());
        newPlayer.setPassword(player.getPassword());

        Player savedPlayer = playerRepository.save(newPlayer);
        return savedPlayer;
    }
}
