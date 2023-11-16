package com.app.tictactoe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "game_info_table")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(length = 9*3) // Assuming a 3x3 Tic-Tac-Toe board and using 3 characters per cell
    private String gameBoard;

    private String firstMove;
    private String winner;
    private boolean gameOver;

    // Getter and Setter for 2D array
    public char[][] getGameBoardArray() {
        char[][] array = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = gameBoard.charAt(i * 3 + j);
            }
        }
        return array;
    }

    public void setGameBoardArray(char[][] array) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : array) {
            sb.append(row);
        }
        this.gameBoard = sb.toString();
    }
}
