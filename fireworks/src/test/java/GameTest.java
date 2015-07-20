import com.ggstudios.fireworks.Game;
import com.ggstudios.fireworks.log.LoggerImpl;

import junit.framework.TestCase;

public class GameTest extends TestCase {
    public void testSimple() {
        Game game = getTestGame();
        game.play(0, 0x0020);
        game.play(1, 0x0040);
        game.play(2, 0x0080);
        game.play(3, 0x0100);
        game.play(4, 0x0200);

        game.play(0, 0x0001);
        game.play(1, 0x0002);
        game.play(2, 0x0004);
        game.play(3, 0x0008);
        game.play(4, 0x0010);

        assertEquals(3, game.getLives());
    }

    public void testLoseLife() {
        Game game = getTestGame();
        game.play(0, 0x0020);
        game.play(1, 0x0040);
        game.play(2, 0x0080);
        game.play(3, 0x0100);
        game.play(4, 0x0800);

        assertEquals(2, game.getLives());

        game.play(0, 0x0400);
        game.play(1, 0x0800);
        game.play(2, 0x0800);

        assertEquals(1, game.getLives());
    }

    public void testGameOver() {
        Game game = getTestGame();
        game.play(0, 0x0020);
        game.play(1, 0x0040);
        game.play(2, 0x0800);
        game.play(3, 0x0800);
        game.play(4, 0x0800);

        assertTrue(game.isGameOver());

        game = getTestGame();
        game.play(0, 0x0800);
        game.play(1, 0x0800);
        game.play(2, 0x0800);
        game.play(3, 0x0800);
        game.play(4, 0x0800);

        assertTrue(game.isGameOver());
    }

    public void testHandDraw() {
        Game game = getTestGame();
        game.drawHand(0);
        game.drawHand(1);
        game.drawHand(2);
        game.drawHand(3);
        game.drawHand(4);

        assertFalse(game.isGameOver());
    }

    public void testLoseOnTurns() {
        Game game = getTestGame();
        int count = 0;
        int lastCard = 1;
        for (; count < 55;) {
            for (int i = 0; i < 5; i++) {
                assertFalse(game.isGameOver());
                int card = game.discard(i, lastCard);
                lastCard = card;
                count++;
            }
        }

        assertTrue(game.isGameOver());
    }

    public void testGameWon() {
        Game game = getTestGame();
        game.play(0, 0x0001);
        game.play(1, 0x0002);
        game.play(2, 0x0004);
        game.play(3, 0x0008);
        game.play(4, 0x0010);

        game.play(0, 0x0020);
        game.play(1, 0x0040);
        game.play(2, 0x0080);
        game.play(3, 0x0100);
        game.play(4, 0x0200);

        game.play(0, 0x0400);
        game.play(1, 0x0800);
        game.play(2, 0x1000);
        game.play(3, 0x2000);
        game.play(4, 0x4000);

        game.play(0, 0x00008000);
        game.play(1, 0x00010000);
        game.play(2, 0x00020000);
        game.play(3, 0x00040000);
        game.play(4, 0x00080000);

        game.play(0, 0x00100000);
        game.play(1, 0x00200000);
        game.play(2, 0x00400000);
        game.play(3, 0x00800000);
        game.play(4, 0x01000000);

        assertTrue(game.isGameOver());
        assertTrue(game.isGameWon());
        assertEquals(3, game.getLives());
    }

    public void testEndangered() {
        Game game = getTestGame();
        assertTrue(game.isEndangered(0x0010));
        assertTrue(game.isEndangered(0x0200));
        assertTrue(game.isEndangered(0x4000));
        assertTrue(game.isEndangered(0x00080000));
        assertTrue(game.isEndangered(0x01000000));

        game.discard(0, 0x0001);
        assertFalse(game.isEndangered(0x0001));
        game.discard(1, 0x0001);
        assertTrue(game.isEndangered(0x0001));
        game.play(2, 0x0001);
        assertFalse(game.isEndangered(0x0001));

        game = getTestGame();
        game.play(0, 0x0001);
        assertFalse(game.isEndangered(0x0001));
        game.discard(1, 0x0001);
        assertFalse(game.isEndangered(0x0001));
        game.discard(2, 0x0001);
        assertFalse(game.isEndangered(0x0001));

        game.play(3, 0x0002);
        game.play(4, 0x0004);
        game.play(0, 0x0008);
        assertTrue(game.isEndangered(0x0010));
        game.play(1, 0x0010);
        assertFalse(game.isEndangered(0x0010));
    }

    private Game getTestGame() {
        Game g = new Game(5);
        g.setLogger(new LoggerImpl());
        g.log(true);
        return g;
    }
}
