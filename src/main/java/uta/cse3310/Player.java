package uta.cse3310;
import org.java_websocket.WebSocket;


public class Player {
  public String name;
  public int score;
  public int index;
  public int gameNum;
  public boolean isReady;
  public WebSocket playerConn;
  public boolean ingame;
  
  Player(String name, WebSocket newConn){
    this.name = name;
    this.isReady = false;
    this.playerConn = newConn;
    this.score = 0;
    this.ingame = false;
  }
}
