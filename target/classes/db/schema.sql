CREATE DATABASE IF NOT EXISTS casezero;

USE casezero;

-- Stores each player's investigation session
CREATE TABLE IF NOT EXISTS game_sessions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL,
    score INT DEFAULT 0,
    status VARCHAR(30) DEFAULT 'IN_PROGRESS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Stores clues collected during each session
CREATE TABLE IF NOT EXISTS collected_clues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    clue_id INT NOT NULL,
    clue_name VARCHAR(100) NOT NULL,
    description TEXT,
    FOREIGN KEY (session_id)
        REFERENCES game_sessions(id),
    UNIQUE (session_id, clue_id)
);