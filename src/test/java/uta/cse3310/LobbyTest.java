package uta.cse3310;
import junit.framework.TestCase;
import java.util.ArrayList;
import org.java_websocket.WebSocket;
import org.mockito.Mockito;

public class LobbyTest extends TestCase {

    public void testLobbyInitialization() {
        ArrayList<Player> players = new ArrayList<>();
        WebSocket socket1 = Mockito.mock(WebSocket.class);
        WebSocket socket2 = Mockito.mock(WebSocket.class);
        Player player1 = new Player("Alice", socket1);
        Player player2 = new Player("Bob", socket2);
        players.add(player1);
        players.add(player2);

        Lobby lobby = new Lobby(players);

        assertEquals(2, lobby.numPlayers);
        assertEquals(0, lobby.numReady);
        assertEquals(0, lobby.gamesAvailable);
        assertTrue(lobby.playerNames.contains("Alice"));
        assertTrue(lobby.playerNames.contains("Bob"));
        assertFalse(lobby.playerStatuses.get(0)); // Alice's status
        assertFalse(lobby.playerStatuses.get(1)); // Bob's status
    }

    public void testUpdateLobby() {
        ArrayList<Player> players = new ArrayList<>();
        WebSocket socket1 = Mockito.mock(WebSocket.class);
        WebSocket socket2 = Mockito.mock(WebSocket.class);
        Player player1 = new Player("Alice", socket1);
        Player player2 = new Player("Bob", socket2);
        players.add(player1);
        players.add(player2);

        Lobby lobby = new Lobby(players);

        // Update players' readiness
        player1.isReady = true;
        player2.isReady = false;

        lobby.updateLobby(players);

        assertEquals(2, lobby.numPlayers);
        assertEquals(1, lobby.numReady);
        assertEquals(0, lobby.gamesAvailable);
        assertTrue(lobby.playerNames.contains("Alice"));
        assertTrue(lobby.playerNames.contains("Bob"));
        assertTrue(lobby.playerStatuses.get(0)); // Alice's status
        assertFalse(lobby.playerStatuses.get(1)); // Bob's status
    }

    public void testUpdateGamesAvailable() {
        Game[] games = new Game[3];
        games[0] = new Game(); // Assume isOpen is true for the first game
        games[1] = new Game(); // Assume isOpen is false for the second game
        games[1].isOpen = false;
        games[2] = new Game(); // Assume isOpen is true for the third game

        Lobby lobby = new Lobby(new ArrayList<>());
        lobby.updateGamesAvailable(games);

        assertEquals(2, lobby.gamesAvailable); // Only two games are open
    }

    public void testlobbylist(){
        int anyplayer = 0;
        ArrayList<Player> players = new ArrayList<>();
        WebSocket socket1 = Mockito.mock(WebSocket.class);
        WebSocket socket2 = Mockito.mock(WebSocket.class);
        WebSocket socket3 = Mockito.mock(WebSocket.class);
        Player player1 = new Player("Alice", socket1);
        Player player2 = new Player("Bob", socket2);
        Player player3 = new Player("Mike", socket3);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        player1.isReady = false;
        player2.isReady = true;
        player3.isReady = true; 
        Lobby lobby = new Lobby(players);  
        assertEquals(3, lobby.numPlayers);
        for(int i = 0; i < lobby.numPlayers; i++){
            if(lobby.playerStatuses.get(i) == true || lobby.playerStatuses.get(i) == false){
                anyplayer++;
            }
        }
        assertEquals(3, anyplayer);       
    }

}
