package com.app.tictactoe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="game_board")
public class GameBoard {
    @Id
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    private int positionNum;
    private String inputSign;
}
