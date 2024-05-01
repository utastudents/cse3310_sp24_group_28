package uta.cse3310;
import uta.cse3310.Matrix;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.lang.String;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONObject; 
import org.json.simple.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


public class Main extends WebSocketServer {
    static String version = System.getenv("VERSION");
    // Vector<Game> games = new Vector<Game>();
    Game[] games = new Game[5];
    // list of used nicknames shared between all the games?
    String[] nicknames = new String[20];
    public ArrayList<Player> playerList = new ArrayList<Player>();
    Lobby lobby = null;
    int playerCount = 0;

    public Main(int port){
        super(new InetSocketAddress(port));
        wordBankList = new ArrayList<>();


    }

    //Ran when a new websocket connection is completed.
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
        conn.send(version);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        boolean playerFound = false;
        // read connection to find the player that dc'ed
        // and delete them
        // this gets rid of having to do another code check for disconnects

        // if player was ready, decrement lobby.numReady by 1

        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).playerConn == conn){
                playerFound = true;
                System.out.println("Player exiting.");
                playerList.remove(i);
                break;
            }
        }
        if(playerFound){
            System.out.printf("[");
            for(Player x: playerList){
                System.out.printf(" %s", x.name);
            }
            // Update lobby probably
            lobby.updateLobby(playerList);
            lobby.updateGamesAvailable(games);
            String jsonString;
            jsonString = gson.toJson(lobby);
            broadcast(jsonString);
            System.out.printf("]\n");
        }
        System.out.println(conn + " has closed");
        if(playerList.size() == 0){
            System.out.println("[RESETTING...]");
            // reset the games
            for(int i = 0; i < 5; i++){
                games[i] = new Game();
                lobby.updateGamesAvailable(games);
            }
            // reset the lobby information;
            lobby.updateLobby(playerList);
        }
    }
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(message);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        UserMsg U = gson.fromJson(message, UserMsg.class);
        System.out.println(U.name + " sent code " + U.code + " over " + conn);
        

        // Possibly crush down the code inside this if statement to a function
        if(U.code == 100){
            System.out.println(U.name + " requesting player slot");
            System.out.println("Current number of players: " + playerList.size());
            //if player count is less than 20, run through the allPlayers
            if(playerList.size() < 20){
                // if the name isn't unique
                if(checkName(U.name) == false){
                    System.out.println("Not unique name");
                    conn.send("unapproved");
                }
                // if the name is unique
                else{
                    Player newPlayer = new Player(U.name, conn);
                    playerList.add(newPlayer);
                    System.out.printf("[");
                    for(Player x: playerList){
                        System.out.printf(" %s", x.name);
                    }
                    System.out.printf("]\n");
                    conn.send("approved");
                    lobby.updateLobby(playerList);
                    lobby.updateGamesAvailable(games);
                    String jsonString;
                    jsonString = gson.toJson(lobby);
                    broadcast(jsonString);
                }
            }
            else{
                System.out.println("Player count at 20.");
            }

        }

        if(U.code == 200){
            //toggle ready state
            for(Player x: playerList){
                // after finding the player who asked for the ready state toggle
                if(x.playerConn == conn){
                    x.isReady = !x.isReady;
                    System.out.println("toggled " + x.name);
                }
            }
            // update lobby
            lobby.updateLobby(playerList);
            lobby.updateGamesAvailable(games);
            String jsonString;
            jsonString = gson.toJson(lobby);
            broadcast(jsonString);
        }
        
        if(U.code == 300){
            int playersReady= 0;
            ArrayList<WebSocket> mailingList = new ArrayList<WebSocket>();
            // Loop through list of games,
            // return game information for the first game available
            for(int i = 0; i < 5; i++){
                if(games[i].isOpen == true){
                    // print out which
                    System.out.println(("Game " + i + " is available"));
                    games[i].isOpen = false;
                    // loop through all players, queue up to 4 ready players.
                    for(Player x: playerList){
                        if(x.isReady == true && playersReady < 4){
                            x.playerConn.send("You will be queued into a game.");
                            x.isReady = false;
                            x.gameNum = i;
                            System.out.println(x.name + " is now in " + x.gameNum);
                            playersReady++;
                            x.index = games[i].numPlayers;
                            games[i].addEntries(x);
                            mailingList.add(x.playerConn);
                        }
                    }
                    for(WebSocket connection : mailingList){
                        String jsonString;
                        jsonString = gson.toJson(games[i]);
                        connection.send(jsonString);
                    }
                    //update lobby information
                    lobby.updateGamesAvailable(games);
                    lobby.updateLobby(playerList);
                    String jsonString;
                    jsonString = gson.toJson(lobby);
                    broadcast(jsonString);
                    jsonString = gson.toJson(games[i]);
                    return;
                }
            }
            System.out.println("No games available.");
        }
    
        if(U.code == 400){
            // check what game they're in
            for(Player x: playerList){
                if(x.playerConn == conn){
                    System.out.println(x.name + " is doing something to " + x.gameNum);
                }
            }
        }

        // NEEDS HEAVY AMOUNT OF REWORK. THIS IS A BAREBONES PROTOTYPE
        if(U.code == 500){
            int destGame = 0;
            int index = 0;
            // find out what game we're working with
            for(Player x: playerList){
                // find who sent it
                if(conn == x.playerConn){
                    destGame = x.gameNum;
                    index = x.index;
                }
            }
            color(gson, index, destGame, U);
        }



        if (U.code == 600) { // 600 is the code for a chat message
            //System.out.println(message);
            //System.out.println(U.name + " sent code " + U.code + " over " + conn);
            handleChatMessage(U);
        }
        
        
    }
    
    
    public List<String> wordBankList; //Keep this for now for the method that displays the words
    
    
     
    public void wordBank(){
        wordBankList = new ArrayList<>();
    }
    
    public void fillWordBank(List<Words> usedWordList){
        if(usedWordList == null || usedWordList.isEmpty()){
            System.out.println("Error: Your list is empty");
            return;
        }
        

        if(!wordBankList.isEmpty()){
            wordBankList.clear();            //Clears the bank before adding in case there's some words left in there from last game
        }   
         
        for(Words w : usedWordList){
            wordBankList.add(w.word);
        }
        
    }
    

 
    public void displayWordsInBank(){
        JSONObject words = new JSONObject();
        JSONArray wordsArray = new JSONArray();                  
        
        for(String word : wordBankList){
            wordsArray.add(word);
        }
        
        
        //rightBox.put("Word Bank", wordsArray);
        words.put("Word Bank", wordsArray); 
        String jsonBank = words.toJSONString();
        broadcast(jsonBank);
    }
    


    public void handleChatMessage(UserMsg userMsg) {
        // Construct JSON object representing the chat message
        JSONObject chatMessage = new JSONObject();
        chatMessage.put("type", "chat");
        chatMessage.put("sender", userMsg.name);
        chatMessage.put("content", userMsg.message);

        // Broadcast the chat message to all connected clients
        String jsonString = chatMessage.toJSONString();
        broadcast(jsonString);
        
        //System.out.println("Sender: " + userMsg.name);
        //System.out.println("Message: " + userMsg.message);
    }

    

    public void color(Gson gson, int index, int destGame, UserMsg U){
        // User wants to highlight a cell.
        System.out.println(games[destGame].temps);
        if(U.endCoords == null){
            System.out.println(U.name + " highlighted cell. Modify temps");
            int[] firstCoord = {U.startCoords[0],U.startCoords[1]};
            games[destGame].highlightCell(index, firstCoord);
        }
        else{
            int y = U.startCoords[0];
            int y2 = U.endCoords[0];
            int x = U.startCoords[1];
            int x2 = U.endCoords[1];
            System.out.printf("[%d, %d] -> [%d, %d]\n", y,x,y2,x2);
            if(y == y2 && x == x2){
                System.out.println("identical coords.");       
                // clear highlighted starting cell for player
                Integer[] clearVal = {-1,-1};
                games[destGame].temps.set(index, clearVal);
            }
            else if(x2 - x == 0 || y2 - y == 0){
                // slope is either vertical or horizontal. check word
                int[] startCoords = {y,x};
                int[] endCoords = {y2,x2};
                boolean isWord = games[destGame].playerFoundWord(startCoords, endCoords);
                if(isWord){
                    Integer currScore = games[destGame].scores.get(index);
                    System.out.println("-----------> " + index);
                    System.out.println("-----------> " + currScore);
                    games[destGame].scores.set(index, currScore + 10);
                    System.out.println("straight checkout returned true. modify colorgrid.");
                    // larger length is the non zero; use this for word length;
                    int length = Math.max(Math.abs(y2-y), Math.abs(x2-x));
                    System.out.println(y2 + " " + y + " " + x2 + " " + x);
                    if(y2-y != 0){
                        // highlight up
                        int end = Math.max(y2,y);
                        for(int i = 0; i < length + 1; i++){
                            System.out.printf("[%d, %d]\n", end -i, x);
                            games[destGame].colorGrid[end - i][x] = indexToChar(index);
                        }
                    }
                    else{
                        // if we're horizontal, start from the end, highlight to start.
                        int end = Math.max(x2,x);
                        for(int i = 0; i < length + 1; i++){
                            System.out.printf("[%d, %d]\n", end, x -i);
                            games[destGame].colorGrid[y][end - i] = indexToChar(index);
                        }
                    }
                    // clear highlighted starting cell for player
                    Integer[] clearVal = {-1,-1};
                    games[destGame].temps.set(index, clearVal);
                }
                else{
                    System.out.println("Word not found or already found.");
                    // clear highlighted starting cell for player
                    Integer[] clearVal = {-1,-1};
                    games[destGame].temps.set(index, clearVal);
                }
            }
            else if(Math.abs((U.endCoords[0] - U.startCoords[0])/(U.endCoords[1] - U.startCoords[1])) == 1){ // diagonal slope
                System.out.println(Math.abs((U.endCoords[0] - U.startCoords[0])/(U.endCoords[1] - U.startCoords[1])));
                int length = Math.max(Math.abs(y2-y), Math.abs(x2-x));
                int endY = U.endCoords[0];
                int endX = U.endCoords[1];
                int[] startCoords = {y,x};
                int[] endCoords = {y2,x2};
                System.out.printf("%d/%d ", y2-y, x2-x);
                boolean isWord = games[destGame].playerFoundWord(startCoords,endCoords);
                if(isWord == true){
                    Integer currScore = games[destGame].scores.get(index);
                    games[destGame].scores.set(index, currScore + 10);
                    System.out.println("found diagonal word.");
                    if(y2-y > 0 && x2-x > 0){
                        System.out.println("Down Right.");
                        for(int i = 0; i < length + 1; i++){
                            System.out.printf("[%d, %d]\n", endY - i, endX - i);
                            games[destGame].colorGrid[endY - i][endX - i] = indexToChar(index);
                        }
                    }
                    else if(y2-y < 0 && x2-x > 0){
                        System.out.println("Up Right.");
                        for(int i = 0; i < length + 1; i++){
                            System.out.printf("[%d, %d]\n", endY + i, endX - i);
                            games[destGame].colorGrid[endY + i][endX - i] = indexToChar(index);
                        }
                    }
                    else if(y2-y > 0 && x2-x < 0){
                        System.out.println("Down Left.");
                        for(int i = 0; i < length + 1; i++){
                            System.out.printf("[%d, %d]\n", endY - i, endX + i);
                            games[destGame].colorGrid[endY - i][endX + i] = indexToChar(index);
                        }
                    }
                    else{
                        System.out.println("Up Left.");
                        for(int i = 0; i < length + 1; i++){
                            System.out.printf("[%d, %d]\n", endY + i, endX + i);
                            games[destGame].colorGrid[endY + i][endX + i] = indexToChar(index);
                        }
                    }
                }
                Integer[] clearVal = {-1,-1};
                games[destGame].temps.set(index, clearVal);
            }
            else{
                // slope is invalid; do nothing
                System.out.println("invalid slope.");
                // clear highlighted starting cell for player
                Integer[] clearVal = {-1,-1};
                games[destGame].temps.set(index, clearVal);
            }}
        for(Player y: playerList){
            // check if the player is in the destined game
            if(games[destGame].names.contains(y.name)){
                String jsonString;
                jsonString = gson.toJson(games[destGame]);
                y.playerConn.send(jsonString);
            }
        }
    }


    // parses user's index to a color-character
    public char indexToChar(int index){
        if(index == 0){
            return 'R';
        }
        else if(index == 1){
            return 'G';
        }
        else if(index == 2){
            return 'B';
        }
        else if(index == 3){
            return 'Y';
        }
        else{
            return '-';
        }
    }
    
    public boolean checkName(String requestedName){
        // check through the nicknames list

        // TODO: Possibly rework this to check through the String[] nicknames instead
        //       depending on how whether the leaderboard lists all players since  
        //       server start or only active players  
        for(Player x : playerList){
            if(x.name.equals(requestedName)){ return false;}
        }
        return true;
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
        // initialize Lobby object
        lobby = new Lobby(playerList);
        //initialize list of games
        for(int i = 0; i < 5; i++){
            games[i] = new Game();
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        // http = 9028;
        int httpport = Integer.parseInt(System.getenv("HTTP_PORT"));
        //int test_grid = Integer.parseInt(System.getenv("TEST_GRID"));
        // Set up the http server
        HttpServer H = new HttpServer(httpport, "./html");
        H.start();
        System.out.println("http Server started on port:" + httpport);

        // create and start the websocket server
        // WSport = 9128;
        int port = Integer.parseInt(System.getenv("WEBSOCKET_PORT"));
        Main A = new Main(port);
        A.start();
        System.out.println("websocket Server started on port: " + port);
    }
}


