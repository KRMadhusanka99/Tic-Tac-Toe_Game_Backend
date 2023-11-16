package com.app.tictactoe.service.implementation;

import com.app.tictactoe.dto.RequestDTO.GameRequestStartDTO;
import com.app.tictactoe.dto.RequestDTO.PlayerRequestTurnDTO;
import com.app.tictactoe.exception.PlayerActiveException;
import com.app.tictactoe.exception.PlayerNotActiveException;
import com.app.tictactoe.exception.PlayerNotExistException;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.repository.GameRepository;
import com.app.tictactoe.repository.PlayerRepository;
import com.app.tictactoe.service.GameService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    // constructor injection
    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Player startGame(GameRequestStartDTO gameRequestStartDTO){
        //check player exist or not
        Player existingPlayer = playerRepository.findByPlayerName(gameRequestStartDTO.getPlayerName());
        if(existingPlayer==null){
            throw new PlayerNotExistException("Player is not registered");
        }

        //check player active or not
        long noOfGamesByPlayer = gameRepository.findByPlayerIdAndGameOver(existingPlayer.getId());
        if(noOfGamesByPlayer > 0){
            throw new PlayerActiveException("Player is already in an active game.");
        }

        //choose who goes first(either "X" or "O")
        String firstMove = Math.random() < 0.5 ? "X": "O";

        // create a game
        Game game = new Game();
        game.setPlayer(existingPlayer);
        game.setFirstMove(firstMove);
        game.setGameOver(false);
        // initialize game board
        if(firstMove.equals("O")) {
            //minimax algorithm to find suitable place to put sign find the winner
            int positionNum = 5;
            char[][] gameBoardArray = new char[3][3];
            gameBoardArray[1][1]='O';
            game.setGameBoardArray(gameBoardArray);
        }else {
            game.setGameBoardArray(new char[3][3]);
        }
        gameRepository.save(game);

        return existingPlayer;
    }

    @Transactional //manage transactions in Springboot
    @Override
    public Player resetGame(String playerName) {
        //check player exist or not
        Player existingPlayer = playerRepository.findByPlayerName(playerName);
        if(existingPlayer==null){
            throw new PlayerNotExistException("Player is not registered");
        }

        //check player active or not
        long noOfGamesByPlayer = gameRepository.findByPlayerIdAndGameOver(existingPlayer.getId());
        if(noOfGamesByPlayer <= 0){
            throw new PlayerNotActiveException("Player is not active in a game.");
        }

        int gameId = gameRepository.gameIdFindByPlayerId(existingPlayer.getId());
        Game game = gameRepository.getReferenceById(gameId);

        game.setGameOver(true);
        game.setWinner("Game Draw");

        gameRepository.save(game);

        //clear game board and
        //start new game with same playerName
        GameRequestStartDTO gameRequestStartDTO = new GameRequestStartDTO(existingPlayer.getPlayerName());

        return startGame(gameRequestStartDTO);
    }

    @Override
    public Player playerTurn(PlayerRequestTurnDTO playerRequestTurnDTO) {

        return null;
    }
}
