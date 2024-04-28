package uta.cse3310;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

public class MainTest extends TestCase{

    public void testOnCloseRemovesPlayerFromPlayerList() {
        // Arrange: Create necessary objects and set up conditions
        ArrayList<Player> playerList = new ArrayList<>();
        Game[] games = new Game[5];
        for(int i = 0; i < 5; i++){
            games[i] = new Game();
        }
        Main main = new Main(8080);
        main.start();



        WebSocket socket1 = Mockito.mock(WebSocket.class);
        WebSocket socket2 = Mockito.mock(WebSocket.class);
        
        // alice and bob joins
        main.onMessage(socket1, "{\"code\":100,\"name\":\"Alice\",\"message\":null,\"startCoords\":[],\"endCoords\":[]}");
        main.onMessage(socket2, "{\"code\":100,\"name\":\"Bob\",\"message\":null,\"startCoords\":[],\"endCoords\":[]}");

        // alice toggles
        main.onMessage(socket1, "{\"code\":200,\"name\":\"Alice\",\"message\":null,\"startCoords\":[],\"endCoords\":[]}");
        
        for(Player x: main.playerList){
            System.out.println(x.isReady);
        }
        assertEquals(true, main.playerList.get(0).isReady);


        // // Act: Call the method to be tested
        main.onClose(socket1, 1001, "Normal closure", true);
        
        assertEquals(1, main.playerList.size());
    }

}