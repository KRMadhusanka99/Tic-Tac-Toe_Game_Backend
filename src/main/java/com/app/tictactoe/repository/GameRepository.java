package com.app.tictactoe.repository;

import com.app.tictactoe.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT COUNT(g) FROM Game g WHERE g.player.id = :playerId AND g.gameOver=false")
    Long findByPlayerIdAndGameOver(int playerId);

    @Query("SELECT g.gameId FROM Game g WHERE g.player.id = :playerId AND g.gameOver=false")
    Integer gameIdFindByPlayerId(int playerId);

}
