package uta.cse3310;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

    public void testOnCloseRemovesPlayerFromPlayerList() {
        // Arrange: Create necessary objects and set up conditions
        Main main = new Main(8080);
        WebSocket socket1 = Mockito.mock(WebSocket.class);
        List<Player> playerList = new ArrayList<>();
        Player player1 = new Player("Alice", socket1);
        Player player2 = new Player("Bob", socket1);
        playerList.add(player1);
        playerList.add(player2);

        // Act: Call the method to be tested
        main.onClose(socket1, 1001, "Normal closure", true);

        // Assert: Check if the method behaves as expected
        assert !playerList.contains(player1); // Player1 should be removed from the playerList
        assert playerList.contains(player2); // Player2 should still be in the playerList
    }

}