package com.example;

// Reworked project structure. compiling should work properly now
// Consider this the main project folder.
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.simple.JSONObject; 

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Vector;
public class Main extends WebSocketServer {
    // Vector<Game> games = new Vector<Game>();
    Game[] games = new Game[5];
    // list of used nicknames shared between all the games?
    String[] nicknames = new String[20];
    public ArrayList<Player> playerList = new ArrayList<Player>();
    Lobby lobby = null;
    int playerCount = 0;
    public Main(int port){
        super(new InetSocketAddress(port));
    }

    //Ran when a new websocket connection is completed.
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
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
            // TODO: check for null names
            System.out.println("Current number of players: " + playerList.size());
            //if player count is less than 20, run through the allPlayers
            if(playerList.size() < 10){
                // if the name isn't unique
                if(checkName(U.name) == false){
                    System.out.println("Not unique name");
                    conn.send("unapproved");
                    // respond accordingly with a message
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
                    
                    // Modify html for user to show their name at the top
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



                    // broadcast the game object to selected players
                    return;
                }
            }

            // if we're in this section of if300, then there are no games open
            // broadcast this to all players in lobby that are ready.
            // since any not ready players will either be in a game or have no need for this

            // Optional: startGame button in interface is only active when there's enough players
            //           and at least 1 game available.
            // check number of open games every time we start a game
            // if the number is zero,
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
    }

    public boolean checkName(String requestedName){
        // check through the nicknames list

        // TODO: Possibly rework this to check through the String[] nicknames instead
        //       depending on how whether the leaderboard lists all players since  
        //       server start or only active players  
        for(Player x : playerList){
            if(x.name.equals(requestedName)){ return false; }
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
        // http = 9028;
        int httpport = Integer.parseInt(System.getenv("HTTP_PORT"));
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
        
        // testGame.placeWord();
    }
}


