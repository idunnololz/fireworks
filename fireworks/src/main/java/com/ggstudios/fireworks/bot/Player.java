package com.ggstudios.fireworks.bot;

import com.ggstudios.fireworks.Game;

public interface Player {
    void setHand(int[] cards);
    void doTurn(Game game);
    int getId();
    int[] getHand();
}
