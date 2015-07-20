package com.ggstudios.fireworks.bot;

public abstract class BasePlayer implements Player {
    private int mId;
    private int[] mCards;

    protected BasePlayer(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    @Override
    public void setHand(int[] cards) {
        mCards = cards;
    }

    @Override
    public int[] getHand() {
        return mCards;
    }
}
