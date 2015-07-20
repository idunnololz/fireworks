package com.ggstudios.fireworks.error;

public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }

    public InvalidMoveException(int whoseTurn, int whoPlayed, String action) {
        super(String.format("Player %d %s on player %d's turn!", whoPlayed, action, whoseTurn));
    }
}
