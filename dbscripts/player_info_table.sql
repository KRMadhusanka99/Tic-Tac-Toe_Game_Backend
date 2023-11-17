-- Create the player_info_table
CREATE TABLE player_info_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    playerName VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
