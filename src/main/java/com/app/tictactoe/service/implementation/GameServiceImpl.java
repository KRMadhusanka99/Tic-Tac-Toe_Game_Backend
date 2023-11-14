package com.app.tictactoe.service.implementation;

import com.app.tictactoe.dto.RequestDTO.GameRequestStartDTO;
import com.app.tictactoe.exception.PlayerActiveException;
import com.app.tictactoe.exception.PlayerNotExistException;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.repository.GameRepository;
import com.app.tictactoe.repository.PlayerRepository;
import com.app.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public Player startGame(GameRequestStartDTO gameRequestStartDTO){
        //check player exist or not
        Player existingPlayer = playerRepository.findByPlayerName(gameRequestStartDTO.getPlayerName());
        if(existingPlayer==null){
            throw new PlayerNotExistException("Player is not registered");
        }

        //check player active or not
        long noOfGamesByPlayer = gameRepository.FindByPlayerIdAndGameOver(existingPlayer.getId());
        if(noOfGamesByPlayer > 0){
            throw new PlayerActiveException("Player is already in an active game.");
        }

        //choose who goes first(either "X" or "O")
        String firstMove = Math.random() < 0.5 ? "X": "O";

        // create a game
        Game game = new Game();
        game.setPlayer(existingPlayer);
        game.setFirstMove(firstMove);

        gameRepository.save(game);
        return existingPlayer;
    }

}
