package com.example.tictactoe.service.implementation;

import com.example.tictactoe.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl {
    @Autowired //inject
    private PlayerRepository playerRepository;
}
