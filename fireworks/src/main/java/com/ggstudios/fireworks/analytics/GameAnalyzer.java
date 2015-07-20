package com.ggstudios.fireworks.analytics;

import com.ggstudios.fireworks.Game;
import com.ggstudios.fireworks.bot.Player;
import com.ggstudios.fireworks.util.GameExecutor;

public class GameAnalyzer {
    private Player[] mPlayers;

    public static class Result {
        public int times;
        public int totalPoints;
        public double averagePointsPerGame;
        public int wins;
        public double winRate;
    }

    public GameAnalyzer(Player[] players) {
        mPlayers = players;
    }

    public Result runExperiment(int times) {
        Result result = new Result();
        for (int i = 0; i < times; i++) {
            GameExecutor ge = new GameExecutor(mPlayers);
            ge.execute();
            Game g = ge.getGame();
            result.totalPoints += g.getPoints();
            result.wins += g.isGameWon() ? 1 : 0;
        }

        result.times = times;
        result.averagePointsPerGame = result.totalPoints / (double) times;
        result.winRate = result.wins / (double) times;
        return result;
    }
}
