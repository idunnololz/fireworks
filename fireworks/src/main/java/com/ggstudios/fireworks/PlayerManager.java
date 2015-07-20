package com.ggstudios.fireworks;

import com.ggstudios.fireworks.bot.Player;

import java.util.Iterator;

public class PlayerManager {
    private Player[] mPlayers;

    public PlayerManager(Player[] players) {
        mPlayers = players;
    }

    /**
     * Gets an iterator starting from the player on the very left, to the player on the very right.
     * @param p
     * @return
     */
    public Iterable<Player> getPlayerIterableFrom(Player p) {
        return new PlayerIterator(p.getId());
    }

    private class PlayerIterator implements Iterable<Player> {
        private int mIndex = 0;
        private int mLast;

        public PlayerIterator(int startingIndex) {
            mIndex = startingIndex;
            mLast = (mIndex - 1) % mPlayers.length;
        }

        @Override
        public Iterator<Player> iterator() {
            return new Iterator<Player>() {
                @Override
                public boolean hasNext() {
                    return mIndex != mLast;
                }

                @Override
                public Player next() {
                    mIndex = (mIndex + 1) % mPlayers.length;
                    return mPlayers[mIndex];
                }
            };
        }
    }
}
