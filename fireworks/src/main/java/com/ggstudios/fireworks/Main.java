package com.ggstudios.fireworks;

import com.ggstudios.fireworks.analytics.GameAnalyzer;
import com.ggstudios.fireworks.bot.BetterBot;
import com.ggstudios.fireworks.bot.CheatingBot;
import com.ggstudios.fireworks.bot.Player;
import com.ggstudios.fireworks.util.GameExecutor;

public class Main {
    public static void main(String[] args) {
        BetterBot[] players = new BetterBot[5];
//        for (int i = 0; i < 5; i++) {
//            players[i] = new CheatingBot(i);
//        }
        for (int i = 0; i < 5; i++) {
            players[i] = new BetterBot(i);
        }
        PlayerManager pm = new PlayerManager(players);
        for (int i = 0; i < 5; i++) {
            players[i].setPlayerManager(pm);
        }
//
//        GameAnalyzer ga = new GameAnalyzer(players);
//        GameAnalyzer.Result r = ga.runExperiment(100000);
//        System.out.println(String.format("Games: %d\nWin rate: %s\nAverage points: %s", r.times,
//                r.winRate, r.averagePointsPerGame));


        GameExecutor ge = new GameExecutor(players);
        ge.execute();
    }
}
