package com.app.tictactoe.service.implementation;

import com.app.tictactoe.dto.RequestDTO.GameRequestStartDTO;
import com.app.tictactoe.dto.RequestDTO.PlayerRequestTurnDTO;
import com.app.tictactoe.exception.*;
import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import com.app.tictactoe.repository.GameRepository;
import com.app.tictactoe.repository.PlayerRepository;
import com.app.tictactoe.service.GameService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        String firstMove = chooseFirstMove();

        // create a game
        Game game = new Game();
        game.setPlayer(existingPlayer);
        game.setFirstMove(firstMove);
        game.setGameOver(false);

        // initialize game board
        char[][] gameBoard = initializeGameBoard(firstMove);
        printGameBoard(gameBoard);
        game.setGameBoardArray(gameBoard);
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
        game.setWinner("Game Rest");

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

        // check valid range of row and column(0-2)
        int row = playerRequestTurnDTO.getRow();
        int column = playerRequestTurnDTO.getColumn();

        if (row < 0 || row > 2 || column < 0 || column > 2) {
            throw new InvalidInputException("Invalid row and column indices. They should be within the range 0 to 2.");
        }

        // check availability row and column
        if(gameBoard[row][column] == 'X' || gameBoard[row][column]=='O'){
            throw new PositionNotAvailableException("Already available position. Try a different row and column. ");
        }

        // add the value
        gameBoard[row][column]='X';

        // wining condition
        // Check if 'X' wins
        if (checkWin(gameBoard, 'X')) {
            // Update game status or do something when 'X' wins
            game.setWinner(playerRequestTurnDTO.getPlayerName());
            game.setGameOver(true);
            printGameBoard(gameBoard);
            game.setGameBoardArray(gameBoard);
            gameRepository.save(game);
            throw new GameOverException("YOU WON!!!!");
        } else if (isBoardFull(gameBoard)) {
                // The game ends in a draw
                game.setWinner("DRAW");
                game.setGameOver(true);
                printGameBoard(gameBoard);
                game.setGameBoardArray(gameBoard);
                gameRepository.save(game);
                throw new GameOverException("GAME TIED!!!!");
            }
            // Call minimax for 'O' move
            int[] bestMove = minimax(gameBoard, 0, true);
            int bestMoveRow = bestMove[1];
            int bestMoveCol = bestMove[2];
            //Make the best move for 'O'
            gameBoard[bestMoveRow][bestMoveCol] = 'O';

            // Check if 'O' wins
            if (checkWin(gameBoard, 'O')) {
                // Update game status or do something when 'O' wins
                game.setWinner("SERVER");
                game.setGameOver(true);
                printGameBoard(gameBoard);
                game.setGameBoardArray(gameBoard);
                gameRepository.save(game);
                throw new GameOverException("YOU LOST!!!!");
            } else if (isBoardFull(gameBoard)) {
                // The game ends in a draw
                game.setWinner("DRAW");
                game.setGameOver(true);
                printGameBoard(gameBoard);
                game.setGameBoardArray(gameBoard);
                gameRepository.save(game);
                throw new GameOverException("GAME TIED!!!!");
            } /*else {
                // Make the best move for 'O'
                gameBoard[bestMoveRow][bestMoveCol] = 'O';
            }*/



        printGameBoard(gameBoard);
        game.setGameBoardArray(gameBoard);
        gameRepository.save(game);
        return existingPlayer;
    }

    // check player username duplication
    private Player isPlayerExist(String playerName){
        Player existingPlayer = playerRepository.findByPlayerName(playerName);
        if(existingPlayer==null){
            throw new PlayerNotExistException("Player is not registered");
        }
        return existingPlayer;
    }

    // check player availability
    private boolean isPlayerAvailable(Player player){
        long noOfGamesByPlayer = gameRepository.findByPlayerIdAndGameOver(player.getId());
        return noOfGamesByPlayer <= 0;
    }

    // who goes first
    private String chooseFirstMove() {
        Random random = new Random();
        return random.nextBoolean() ? "X" : "O";
    }

    // initialize board
    private char[][] initializeGameBoard(String firstMove) {
        char[][] gameBoard = new char[3][3];

        if (firstMove.equals("O")) {
            // Use minimax to find the optimal move for 'O'
            minimaxInitialMove(gameBoard);
        }

        return gameBoard;
    }

    // check winner
    private boolean checkWin(char[][] gameBoard, char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((gameBoard[i][0] == player && gameBoard[i][1] == player && gameBoard[i][2] == player) ||
                    (gameBoard[0][i] == player && gameBoard[1][i] == player && gameBoard[2][i] == player)) {
                return true;
            }
        }

        // Check diagonals
        return (gameBoard[0][0] == player && gameBoard[1][1] == player && gameBoard[2][2] == player) ||
                (gameBoard[0][2] == player && gameBoard[1][1] == player && gameBoard[2][0] == player);
    }

    // minimax algorithm
    /*private int minimax(char[][] gameBoard, int depth, boolean maximizingPlayer) {
        // Check if the game is over or if it's a terminal state
        if (checkWin(gameBoard, 'O')) {
            return 1; // 'O' wins
        } else if (checkWin(gameBoard, 'X')) {
            return -1; // 'X' wins
        } else if (isBoardFull(gameBoard)) {
            return 0; // It's a draw
        }

        if (maximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard[i][j] == '\u0000') { // Check if the cell is empty
                        gameBoard[i][j] = 'O'; // Make the move for 'O'
                        int score = minimax(gameBoard, depth + 1, false);
                        gameBoard[i][j] = '\u0000'; // Undo the move
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (gameBoard[i][j] == '\u0000') { // Check if the cell is empty
                        gameBoard[i][j] = 'X'; // Make the move for 'X'
                        int score = minimax(gameBoard, depth + 1, true);
                        gameBoard[i][j] = '\u0000'; // Undo the move
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
*/
    private int[] minimax(char[][] gameBoard, int depth, boolean maximizingPlayer) {
        // Check if the game is over or if it's a terminal state
        if (checkWin(gameBoard, 'O')) {
            return new int[]{1, -1, -1}; // 'O' wins
        } else if (checkWin(gameBoard, 'X')) {
            return new int[]{-1, -1, -1}; // 'X' wins
        } else if (isBoardFull(gameBoard)) {
            return new int[]{0, -1, -1}; // It's a draw
        }

        int bestScore = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestMoveRow = -1;
        int bestMoveCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == '\u0000') { // Check if the cell is empty
                    gameBoard[i][j] = maximizingPlayer ? 'O' : 'X'; // Make the move
                    int[] scoreAndMove = minimax(gameBoard, depth + 1, !maximizingPlayer);
                    gameBoard[i][j] = '\u0000'; // Undo the move

                    int score = scoreAndMove[0];
                    if ((maximizingPlayer && score > bestScore) || (!maximizingPlayer && score < bestScore)) {
                        bestScore = score;
                        bestMoveRow = i;
                        bestMoveCol = j;
                    }
                }
            }
        }

        return new int[]{bestScore, bestMoveRow, bestMoveCol};
    }


    private boolean isBoardFull(char[][] gameBoard) {
        // Check if the game board is full
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == '\u0000') {
                    return false; // Found an empty cell
                }
            }
        }
        return true; // All cells are filled
    }

    private void minimaxInitialMove(char[][] gameBoard) {
        int bestScore = Integer.MIN_VALUE;
        int bestMoveRow = -1;
        int bestMoveCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameBoard[i][j] == '\u0000') { // Check if the cell is empty
                    gameBoard[i][j] = 'O'; // Make the move for 'O'
                    int[] score = minimax(gameBoard, 0, false);
                    gameBoard[i][j] = '\u0000'; // Undo the move

                    // Update the best move if the current move has a higher score
                    if (score[0] > bestScore) {
                        bestScore = score[0];
                        bestMoveRow = i;
                        bestMoveCol = j;
                    }
                }
            }
        }

        // Make the best move for 'O'
        gameBoard[bestMoveRow][bestMoveCol] = 'O';
    }

    // Helper method to print the current game board
    private void printGameBoard(char[][] gameBoard) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(gameBoard[i][j]+" ");
            }
            System.out.println();
        }
    }
}
