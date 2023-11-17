-- Create the game_info_table
CREATE TABLE game_info_table (
     gameId INT AUTO_INCREMENT PRIMARY KEY,
     player_id INT,
     gameBoard VARCHAR(27) NOT NULL,
     firstMove VARCHAR(255),
     winner VARCHAR(255),
     gameOver BOOLEAN,
     FOREIGN KEY (player_id) REFERENCES player_info_table(id)
);