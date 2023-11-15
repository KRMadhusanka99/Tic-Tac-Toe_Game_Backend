package com.app.tictactoe.repository;

import com.app.tictactoe.model.GameBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameBoardRepository extends JpaRepository<GameBoard,Integer> {
}
