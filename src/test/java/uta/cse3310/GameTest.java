package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.java_websocket.WebSocket;

import junit.framework.TestCase;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Random;

public class GameTest extends TestCase {

    public void testGameInitialization() {
        Game game = new Game();

        assertTrue(game.isOpen);
        assertEquals(0, game.numPlayers);
        assertEquals(30, game.matrix.length);
        assertEquals(30, game.matrix[0].length);
        assertEquals(30, game.colorGrid.length);
        assertEquals(30, game.colorGrid[0].length);
        assertEquals(0, game.temps.size());
        assertEquals(0, game.scores.size());
        assertEquals(0, game.names.size());
    }

    public void testAddEntries() {
        Game game = new Game();
        Player player1 = Mockito.mock(Player.class);
        Player player2 = Mockito.mock(Player.class);

        game.addEntries(player1);
        game.addEntries(player2);

        assertEquals(2, game.scores.size());
        assertEquals(2, game.names.size());
        assertEquals(2, game.temps.size());
        assertEquals(-1, (int) game.temps.get(0)[0]); // Check if initialized to -1
        assertEquals(-1, (int) game.temps.get(0)[1]); // Check if initialized to -1
        assertEquals(-1, (int) game.temps.get(1)[0]); // Check if initialized to -1
        assertEquals(-1, (int) game.temps.get(1)[1]); // Check if initialized to -1
        assertEquals(2, game.numPlayers);
    }

}
