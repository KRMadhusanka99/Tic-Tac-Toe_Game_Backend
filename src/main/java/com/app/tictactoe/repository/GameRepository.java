package com.app.tictactoe.repository;

import com.app.tictactoe.model.Game;
import com.app.tictactoe.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
    Player findPlayerAndGameOver(Player player, Boolean gameOver);
}
