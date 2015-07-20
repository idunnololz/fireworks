package com.ggstudios.fireworks;

public class CardUtils {
    public static class Color {
        public static final int BLUE = 0;
        public static final int GREEN = 1;
        public static final int RED = 2;
        public static final int WHITE = 3;
        public static final int YELLOW = 4;
    }

    public static final int[] COLORS = {
            0, 1, 2, 3, 4
    };

    public static final int[] NUMBERS = {
            1, 2, 3, 4, 5
    };

    private static final int BLUE_INDEX = 0;
    private static final int GREEN_INDEX = 1;
    private static final int RED_INDEX = 2;
    private static final int WHITE_INDEX = 3;
    private static final int YELLOW_INDEX = 4;

    private static final String[] COLOR_NAMES = new String[] {
            "BLUE", "GREEN", "RED", "WHITE", "YELLOW"
    };

    private static final String[] NUMBER_NAMES = new String[] {
            "ONE", "TWO", "THREE", "FOUR", "FIVE"
    };

    private static int[] COLOR_MASKS = new int[] {
            0x1F, 0x3E0, 0x7C00, 0xF8000, 0x1F00000
    };

    public static final int[] FIVES = new int[] {
            0x0010, 0x0200, 0x4000, 0x00080000, 0x01000000
    };

    private static final int BLUE_MASK = 0x1F;
    private static final int GREEN_MASK = 0x3E0;
    private static final int RED_MASK = 0x7C00;
    private static final int WHITE_MASK = 0xF8000;
    private static final int YELLOW_MASK = 0x1F00000;

    private static final int BLUE_BIT_INDEX = 0;
    private static final int GREEN_BIT_INDEX = 5;
    private static final int RED_BIT_INDEX = 10;
    private static final int WHITE_BIT_INDEX = 15;
    private static final int YELLOW_BIT_INDEX = 20;

    private static final StringBuilder sStringBuilder = new StringBuilder();

    public static String getCardName(int id) {
        sStringBuilder.setLength(0);

        if ((id & BLUE_MASK) != 0) {
            sStringBuilder.append(COLOR_NAMES[0]);
        } else if ((id & GREEN_MASK) != 0) {
            sStringBuilder.append(COLOR_NAMES[1]);
            id >>= GREEN_BIT_INDEX;
        } else if ((id & RED_MASK) != 0) {
            sStringBuilder.append(COLOR_NAMES[2]);
            id >>= RED_BIT_INDEX;
        } else if ((id & WHITE_MASK) != 0) {
            sStringBuilder.append(COLOR_NAMES[3]);
            id >>= WHITE_BIT_INDEX;
        } else if ((id & YELLOW_MASK) != 0) {
            sStringBuilder.append(COLOR_NAMES[4]);
            id >>= YELLOW_BIT_INDEX;
        }
        sStringBuilder.append(" ");

        if ((id & 0b11) != 0) {
            if (id == 0b1) {
                sStringBuilder.append(NUMBER_NAMES[0]);
            } else {
                sStringBuilder.append(NUMBER_NAMES[1]);
            }
        } else {
            if (id == 0b100) {
                sStringBuilder.append(NUMBER_NAMES[2]);
            } else if (id == 0b1000) {
                sStringBuilder.append(NUMBER_NAMES[3]);
            } else {
                sStringBuilder.append(NUMBER_NAMES[4]);
            }
        }
        return sStringBuilder.toString();
    }

    public static int getNextPlayable(int card, int color) {
        card = (card << 1) & COLOR_MASKS[color];
        return card;
    }

    public static String getHint(int hint) {
        if (hint < 5) {
            return COLOR_NAMES[hint];
        }
        return NUMBER_NAMES[hint - 5];
    }

    public static boolean isOne(int card) {
        return card == 0x0001 || card == 0x0020 || card == 0x0400
                || card == 0x00008000 || card == 0x00100000;
    }

    private static final int[] DECK = new int[] {
            0x00000001,
            0x00000001,
            0x00000001,
            0x00000002,
            0x00000002,
            0x00000004,
            0x00000004,
            0x00000008,
            0x00000008,
            0x00000010,
            0x00000020,
            0x00000020,
            0x00000020,
            0x00000040,
            0x00000040,
            0x00000080,
            0x00000080,
            0x00000100,
            0x00000100,
            0x00000200,
            0x00000400,
            0x00000400,
            0x00000400,
            0x00000800,
            0x00000800,
            0x00001000,
            0x00001000,
            0x00002000,
            0x00002000,
            0x00004000,
            0x00008000,
            0x00008000,
            0x00008000,
            0x00010000,
            0x00010000,
            0x00020000,
            0x00020000,
            0x00040000,
            0x00040000,
            0x00080000,
            0x00100000,
            0x00100000,
            0x00100000,
            0x00200000,
            0x00200000,
            0x00400000,
            0x00400000,
            0x00800000,
            0x00800000,
            0x01000000,
    };

    public static int[] getRawDeck() {
        return DECK;
    }
}
