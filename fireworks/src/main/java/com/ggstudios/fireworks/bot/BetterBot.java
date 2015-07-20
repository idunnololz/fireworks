package com.ggstudios.fireworks.bot;

import com.ggstudios.fireworks.CardUtils;
import com.ggstudios.fireworks.Game;
import com.ggstudios.fireworks.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class BetterBot extends BasePlayer {

    private PlayerManager mPlayerManager;

    private int mTurn;
    private int mLastHint;

    private PartialCard[] mPublicCardKnowledge;
    private PartialCard[] mInferredCardKnowledge;

    private Stack<Integer> mPlayStack = new Stack<Integer>();

    public BetterBot(int id) {
        super(id);

        mTurn = 0;
    }

    public void setPlayerManager(PlayerManager pm) {
        mPlayerManager = pm;
    }

    @Override
    public void setHand(int[] cards) {
        super.setHand(cards);
        mPublicCardKnowledge = new PartialCard[cards.length];
        mInferredCardKnowledge = new PartialCard[cards.length];
        for (int i = 0; i < cards.length; i++) {
            mPublicCardKnowledge[i] = new PartialCard();
            mInferredCardKnowledge[i] = new PartialCard();
        }
    }

    public PartialCard[] getCardKnowledge() {
        return mPublicCardKnowledge;
    }

    @Override
    public void doTurn(Game game) {
        /*
         * This bot uses the play conventions of:
         * - Play from right, discard from left
         * - Bluffs
         * - Finesse
         * - Out of order finesse
         * - Stack based multi-playable hints
         */

        // if we were hinted to play something...
        if (!mPlayStack.empty()) {
            
        }

        if (mTurn == 0) {
            // this is the first turn... try to hint ones...
            for (Player p : mPlayerManager.getPlayerIterableFrom(this)) {
                // try to find the person with the most hint-able ones
                BetterBot bot = (BetterBot) p;
                bot.getHand();
                PartialCard[] k = bot.getCardKnowledge();


            }
        }


        mTurn++;
    }

    public static class PartialCard {
        public int number;
        public int color;
        public int lastHinted;

        public List<Integer> possibleColor = new ArrayList<>();
        public List<Integer> possibleNumber = new ArrayList<>();

        public PartialCard() {
            for (int c : CardUtils.COLORS) {
                possibleColor.add(c);
            }
            for (int n : CardUtils.NUMBERS) {
                possibleNumber.add(n);
            }
        }
    }
}
