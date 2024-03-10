package com.example;

// Reworked project structure. compiling should work properly now
// Consider this the main project folder.
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.util.Vector;
public class Main extends WebSocketServer {
    Vector<Game> games = new Vector<Game>();

    public Main(int port){
        super(new InetSocketAddress(port));
    }

    //Ran when a new websocket connection is completed.
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");
        Game testGame = null;
        testGame = new Game();
        testGame.initGrid();
        testGame.placeWord();
        
        // attaches this specific Game object instance to the 
        // specific ws connection that just opened
        conn.setAttachment(testGame);
        Gson gson = new Gson();
        String json = gson.toJson(testGame.grid);
        broadcast(json);
        // JsonArray gameArray = new JsonArray(Arrays.asList({1,2,3}));
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " has closed");
    }
    
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println(message);
        Game G = conn.getAttachment();
        G.printGrid();
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("Server started!");
        setConnectionLostTimeout(0);
    }



    public static void main(String[] args) {
        // Game game = new Game();
        // game.initGrid();
        // game.placeWord();
        // game.printGrid();
            // Set up the http server
        int port = 9080;
        HttpServer H = new HttpServer(port, "./html");
        H.start();
        System.out.println("http Server started on port:" + port);

        // create and start the websocket server

        port = 9880;
        Main A = new Main(port);
        A.start();
        System.out.println("websocket Server started on port: " + port);
        
        // testGame.placeWord();
    }
}