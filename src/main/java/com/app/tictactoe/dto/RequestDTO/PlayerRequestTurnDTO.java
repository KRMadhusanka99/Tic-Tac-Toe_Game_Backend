package com.app.tictactoe.dto.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequestTurnDTO {
    private String playerName;
    /*private int row;
    private int column;*/
}
