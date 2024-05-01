package uta.cse3310;

import junit.framework.TestCase;
import org.java_websocket.WebSocket;
import org.mockito.Mockito;

public class PlayerTest extends TestCase {

    public void testPlayerInitialization() {
        WebSocket socket = Mockito.mock(WebSocket.class);
        Player player = new Player("Alice", socket);
        Player player2 = new Player("abcdefghijklmnopqrstuvwxyz", socket);
        assertEquals("Alice", player.name);
        assertEquals(0, player.score);
        assertEquals(false, player.isReady);
        assertEquals(socket, player.playerConn);
        assertFalse(player2.name.length() <= 25);
    }

}