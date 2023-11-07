package com.app.tictactoe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getters and Setters adding using lombok
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)

    @NotEmpty(message = "Player name is required")
    private String playerName;
    @NotEmpty(message = "Password is required")
    private String password;
}
