package com.app.tictactoe.dto.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequestRegisterDTO {
    @NotNull
    @NotBlank
    private String playerName;
    @NotNull
    @NotBlank
    private String password;
}
