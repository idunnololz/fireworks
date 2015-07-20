package com.ggstudios.fireworks.bot;

import com.ggstudios.fireworks.Game;

public class CheatingBot extends BasePlayer {
    int[] mHand;

    public CheatingBot(int id) {
        super(id);
    }

    @Override
    public void setHand(int[] cards) {
        super.setHand(cards);
        mHand = cards;
    }

    @Override
    public void doTurn(Game game) {
        for (int i = 0; i < mHand.length; i++) {
            int c = mHand[i];
            if (game.isPlayable(c)) {
                mHand[i] = game.play(getId(), c);
                return;
            }
        }

        if (game.getHints() != 0) {
            game.hint(getId(), 0, 1);
            return;
        }

        for (int i = 0; i < mHand.length; i++) {
            int c = mHand[i];
            if (!game.isEndangered(c)) {
                mHand[i] = game.discard(getId(), c);
                return;
            }
        }

        for (int i = 0; i < mHand.length; i++) {
            int c = mHand[i];
            if (c != 0) {
                mHand[i] = game.discard(getId(), c);
                return;
            }
        }
    }
}
