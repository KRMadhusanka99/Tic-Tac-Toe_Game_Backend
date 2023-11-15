package com.app.tictactoe.repository;

import com.app.tictactoe.model.GameBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameBoardRepository extends JpaRepository<GameBoard,Integer> {

    @Query("SELECT g FROM GameBoard g WHERE g.game.gameId = :gameId")
    List<GameBoard> findAllByGameId(int gameId);

    //rather than using select query
    @Modifying
    @Query("DELETE FROM GameBoard g WHERE g.game.gameId= :gameId")
    void deleteGameBoardByGameId(int gameId);
}
