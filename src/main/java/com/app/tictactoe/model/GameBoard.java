package com.app.tictactoe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class GameBoard {

    @Id
    private int gameId;
    private int roundNo;
    private String userPosition;
    private String serverPosition;
}
