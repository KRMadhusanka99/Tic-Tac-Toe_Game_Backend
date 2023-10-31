package com.app.tictactoe.service.implementation;

import com.app.tictactoe.repository.PlayerRepository;
import com.app.tictactoe.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired //inject
    private PlayerRepository playerRepository;
}
