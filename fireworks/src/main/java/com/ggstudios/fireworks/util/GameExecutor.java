package com.ggstudios.fireworks.util;

import com.ggstudios.fireworks.Game;
import com.ggstudios.fireworks.bot.Player;
import com.ggstudios.fireworks.log.LoggerImpl;

public class GameExecutor {
    private Game mGame;
    private Player[] mPlayers;

    public GameExecutor(Player[] players) {
        mGame = new Game(players.length);
        mPlayers = players;
    }

    public void log() {
        mGame.setLogger(new LoggerImpl());
        mGame.log(true);
    }

    public int execute() {
        for (Player p : mPlayers) {
            p.setHand(mGame.drawHand(p.getId()));
        }
        loop:
        while (true) {
            for (Player p : mPlayers) {
                p.doTurn(mGame);
                if (mGame.isGameOver()) {
                    break loop;
                }
            }
        }

        return mGame.getPoints();
    }

    public Game getGame() {
        return mGame;
    }
}
