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

import java.util.Optional;
import java.util.Random;


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
        Player existingPlayer = isPlayerExist(gameRequestStartDTO.getPlayerName());

        //check player active or not
        if(!isPlayerAvailable(existingPlayer)){
            throw new PlayerActiveException("Player is already in an active game.");
        }

        //choose who goes first(either "X" or "O")
        Random random = new Random();
        String firstMove = random.nextBoolean() ? "X": "O";

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
            gameBoardArray[0][0]='O';
            gameBoardArray[0][1]='X';
            gameBoardArray[0][2]='X';
            gameBoardArray[1][0]='X';
            gameBoardArray[1][1]='O';
            gameBoardArray[1][2]='O';
            gameBoardArray[2][0]='X';
            gameBoardArray[2][1]='X';
            gameBoardArray[2][2]='O';
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
        Player existingPlayer = isPlayerExist(playerName);

        //check player active or not
        if(isPlayerAvailable(existingPlayer)){
            throw new PlayerNotActiveException("Player is not active in a game.");
        }

        int gameId = gameRepository.gameIdFindByPlayerId(existingPlayer.getId());
        Game game = gameRepository.getReferenceById(gameId);

        game.setGameOver(true);
        //todo: if win the game set the winner name rather than game draw
        game.setWinner("Game Draw");

        gameRepository.save(game);

        //clear game board and
        //start new game with same playerName
        GameRequestStartDTO gameRequestStartDTO = new GameRequestStartDTO(existingPlayer.getPlayerName());

        return startGame(gameRequestStartDTO);
    }

    @Override
    public Player playerTurn(PlayerRequestTurnDTO playerRequestTurnDTO) {
        //check player exist or not
        Player existingPlayer = isPlayerExist(playerRequestTurnDTO.getPlayerName());

        //check player active or not
        if(isPlayerAvailable(existingPlayer)){
            throw new PlayerNotActiveException("Player is not active in a game.");
        }

        // get the game board for move
        int gameId = gameRepository.gameIdFindByPlayerId(existingPlayer.getId());
        Game game = gameRepository.getReferenceById(gameId);
        char [][] gameBoard= game.getGameBoardArray();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.println();
        }
        return null;
    }

    // check player username duplication
    public Player isPlayerExist(String playerName){
        Player existingPlayer = playerRepository.findByPlayerName(playerName);
        if(existingPlayer==null){
            throw new PlayerNotExistException("Player is not registered");
        }
        return existingPlayer;
    }

    // check player availability
    public boolean isPlayerAvailable(Player player){
        long noOfGamesByPlayer = gameRepository.findByPlayerIdAndGameOver(player.getId());
        if(noOfGamesByPlayer <= 0){
            return true;
        }else{
            return false;
        }
    }
}
