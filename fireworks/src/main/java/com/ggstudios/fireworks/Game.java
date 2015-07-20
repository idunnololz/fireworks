package com.ggstudios.fireworks;

import com.ggstudios.fireworks.error.InvalidMoveException;
import com.ggstudios.fireworks.log.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.smartcardio.Card;

/**
 * Defines the rules to fireworks and provides a method of interfacing with the game.
 */
public class Game {
    private static final String TAG = Game.class.getSimpleName();

    private static final int NUM_COLORS = 5;

    private final int mPlayers;

    /**
     * Which player's mTurn it is
     */
    private int mTurn;

    private Deck mDeck;

    private int mLives;
    private int mHints;

    private boolean mGameEnded;
    private boolean mLog = false;

    private int[] mValidPlays = new int[NUM_COLORS];
    private Logger mLogger;

    private int mTurnsLeft;

    private Set<Integer> mPlayed = new HashSet<>();
    private Map<Integer, Integer> mEndangered = new HashMap<>();
    private Set<Integer> mDanger = new HashSet<>();
    private int points;

    public Game(int players) {
        mPlayers = players;

        mDeck = new Deck();

        mTurn = 0;
        mLives = 3;
        mHints = 8;

        mTurnsLeft = Integer.MAX_VALUE;

        mGameEnded = false;

        for (int i = 0; i < mValidPlays.length; i++) {
            mValidPlays[i] = 1 << (i*5);
        }

        for (int c : CardUtils.FIVES) {
            mDanger.add(c);
            mEndangered.put(c, 1);
        }
    }

    public int getTurn() {
        return mTurn;
    }

    public int[] drawHand(int player) {
        if (mTurn != player) {
            throw new InvalidMoveException(mTurn, player, "gave a hint");
        }
        int[] cards;
        if (mPlayers < 4) {
            cards = new int[] {
                    mDeck.pop(),
                    mDeck.pop(),
                    mDeck.pop(),
                    mDeck.pop(),
                    mDeck.pop(),
            };
        } else {
            cards = new int[] {
                    mDeck.pop(),
                    mDeck.pop(),
                    mDeck.pop(),
                    mDeck.pop(),
            };
        }

        if (mLog) {
            StringBuilder sb = new StringBuilder();
            for (int c : cards) {
                sb.append(CardUtils.getCardName(c));
                sb.append(", ");
            }
            sb.setLength(sb.length() - 2);

            mLogger.d(TAG, String.format("Player %d drew %s.", player, sb.toString()));
        }

        advanceTurn();
        return cards;
    }

    public void hint(int player, int toPlayer, int hint) {
        if (mTurn != player) {
            throw new InvalidMoveException(mTurn, player, "gave a hint");
        }

        if (mHints == 0) {
            throw new InvalidMoveException("Cannot give a hint when no hints available!");
        }
        mHints--;

        if (mLog) {
            mLogger.d(TAG, String.format("Player %d hinted player %d's %s.", player, toPlayer,
                    CardUtils.getHint(hint)));
        }

        advanceTurn();
    }

    public int play(int player, int card) {
        if (mTurn != player) {
            throw new InvalidMoveException(mTurn, player, "played " + CardUtils.getCardName(card));
        }

        if (mLog) {
            mLogger.d(TAG, String.format("Player %d played %s.", player,
                    CardUtils.getCardName(card)));
        }

        boolean playable = false;
        for (int i = 0; i < mValidPlays.length; i++) {
            if (card == mValidPlays[i]) {
                int next = CardUtils.getNextPlayable(card, i);
                mValidPlays[i] = next;
                if (next == 0) {
                    checkForWin();
                }
                playable = true;
                mPlayed.add(card);
                play(card);
                break;
            }
        }
        if (!playable) {
            loseLife();
            discard(card);
        }
        advanceTurn();
        return draw(player);
    }

    public int discard(int player, int card) {
        if (mTurn != player) {
            throw new InvalidMoveException(mTurn, player, "played " + CardUtils.getCardName(card));
        }
        if (mLog) {
            mLogger.d(TAG, String.format("Player %d discarded %s.", player,
                    CardUtils.getCardName(card)));
        }
        discard(card);
        gainHint();
        advanceTurn();
        return draw(player);
    }

    public boolean isPlayable(int card) {
        for (int i = 0; i < mValidPlays.length; i++) {
            if (card == mValidPlays[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean isEndangered(int card) {
        return mDanger.contains(card);
    }

    public void play(int card) {
        mEndangered.remove(card);
        mDanger.remove(card);
    }

    private void discard(int card) {
        if (mPlayed.contains(card)) return;

        Integer v = mEndangered.get(card);
        if (v == null) {
            if (CardUtils.isOne(card)) {
                mEndangered.put(card, 2); // two more
            } else {
                mEndangered.put(card, 1);
                mDanger.add(card);
            }
        } else {
            if (v == 2) {
                mEndangered.put(card, 1);
                mDanger.add(card);
            } else {
                mEndangered.put(card, 0);
            }
        }
    }

    private int draw(int player) {
        if (mDeck.isEmpty()) return 0;
        int card = mDeck.pop();

        if (mLog) {
            mLogger.d(TAG, String.format("Player %d drew a %s", player,
                    CardUtils.getCardName(card)));
        }

        if (mDeck.isEmpty()) {
            mTurnsLeft = mPlayers;

            if (mLog) {
                mLogger.d(TAG, "Deck is empty!");
            }
        }

        return card;
    }

    public int getLives() {
        return mLives;
    }

    public boolean isGameOver() {
        return mGameEnded;
    }

    public boolean isGameWon() {
        for (int c : mValidPlays) {
            if (c != 0) return false;
        }
        return true;
    }

    private void checkForWin() {
        if (isGameWon()) {
            endGame();
        }
    }

    private void loseLife() {
        if (!mGameEnded) {
            mLives--;
            if (mLog) {
                mLogger.d(TAG, String.format("Lost a life. %d lives left.", mLives));
            }
            if (mLives == 0) {
                endGame();
            }
        }
    }

    private void endGame() {
        if (mLog) {
            mLogger.d(TAG, "The game has ended.");
        }
        mGameEnded = true;
    }

    private void gainHint() {
        if (mHints < 8) {
            if (mLog) {
                mLogger.d(TAG, String.format("Hint gained. %d hints left.", mHints));
            }
            mHints++;
        }
    }

    private void advanceTurn() {
        mTurn = (mTurn + 1) % mPlayers;
        mTurnsLeft--;
        if (mTurnsLeft == 0) {
            endGame();
        }
    }

    public void setLogger(Logger logger) {
        mLogger = logger;
    }

    public void log(boolean shouldLog) {
        mLog = shouldLog;
    }

    public int getHints() {
        return mHints;
    }

    public int getPoints() {
        return mPlayed.size();
    }
}
