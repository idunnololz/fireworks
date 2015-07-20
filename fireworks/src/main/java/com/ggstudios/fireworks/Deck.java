package com.ggstudios.fireworks;

import com.ggstudios.fireworks.util.MersenneTwisterFast;

import java.util.ArrayList;

public class Deck extends ArrayList<Integer> {
    private static MersenneTwisterFast rand = new MersenneTwisterFast(System.currentTimeMillis());

    public Deck() {
        reset();
    }

    private void reset() {
        populate();
        shuffle();
    }

    private void populate() {
        int[] d = CardUtils.getRawDeck();
        for (int c : d) {
            add(c);
        }
    }

    private void shuffle() {
        final int len = size();
        for (int i = 0; i < len; i++) {
            int swap = get(i);

            int r = i + rand.nextInt(len - i);

            set(i, get(r));
            set(r, swap);
        }
    }

    public int pop() {
        final int lastIndex = size() - 1;
        final int last = get(lastIndex);
        remove(lastIndex);
        return last;
    }
}
