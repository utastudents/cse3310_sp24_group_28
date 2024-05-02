package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.java_websocket.WebSocket;

import junit.framework.TestCase;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class GameTest extends TestCase {
    private Game game;


    // public void testPlayersCannotJoinOnceGameHasBegun() {
    //     // Add some players to the game
    //     ArrayList<Player> players = new ArrayList<>();
    //     players.add(new Player("Player1", null));
    //     players.add(new Player("Player2", null));
    //     game = new Game(players, 1); // Assuming game number is 1

    //     // Now, let's simulate the game has begun
    //     // Set the isOpen flag to false
    //     game.isOpen = false;

    //     // Try to add a new player
    //     Player newPlayer = new Player("NewPlayer", null);
    //     game.addEntries(newPlayer);

    //     // Verify that the new player has not been added
    //     assertTrue("New player should not be able to join once the game has begun", game.verifyPlayer(newPlayer));
    // }

    // public void setUp() {
    //     // Initialize the game object before each test
    //     game = new Game();
    // }


    // public void testScoreboardIsUpdatedAfterGame() {
    //     setUp();
    //     // Add players to the game
    //     Player player1 = new Player("Player1", null);
    //     Player player2 = new Player("Player2", null);

    //     ArrayList<Player> players = new ArrayList<>();
    //     players.add(player1);
    //     players.add(player2);
    //     game = new Game(players, 1); // Assuming game number is 1

    //     // Simulate the game
    //     // For simplicity, let's just manually update the scores
    //     player1.score = 10;
    //     player2.score = 20;

    //     // End the game
    //     game.gameFinished();

    //     // Check if the scoreboard is updated
    //     ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    //     System.setOut(new PrintStream(outContent));
    //     game.printScoreBoard();

    //     String expectedOutput = "--------- Scoreboard ----------\n" +
    //                             "Player1 :  10\n" +
    //                             "Player2 :  20\n";
    //     assertEquals(expectedOutput, outContent.toString(), "Scoreboard should display updated scores");
    // }

    public void testGameInitialization() {
        Game game = new Game();

        assertTrue(game.isOpen);
        assertEquals(0, game.numPlayers);
        assertEquals(30, game.matrixDup.length);
        assertEquals(30, game.matrixDup[0].length);
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
