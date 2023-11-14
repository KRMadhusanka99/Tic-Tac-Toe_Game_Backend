package com.app.tictactoe.service.implementation;

import com.app.tictactoe.repository.GameRepository;
import com.app.tictactoe.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;

public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;
}
