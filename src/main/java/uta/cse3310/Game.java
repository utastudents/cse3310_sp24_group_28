package uta.cse3310;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Timer;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
public class Game {

  
  public int gameNum;   //coorealates games and corresponding players
  public Matrix m = new Matrix(); 
  public ArrayList<Player> players; //players in this game can also get the 'numPlayers' easily
  public ArrayList<Words> wordsFound  = new ArrayList<Words>(); //words found in the game
  public ArrayList<String> wordBank = new ArrayList<String>();  //easily get a hold of the wordbank to display during a game



  // Possible attributes needed
  public boolean isOpen;
  public int numPlayers;
  public char[][] matrix = m.grid;
  public char[][] colorGrid = new char[30][30];
  // temps is a list of coordinate pairs. Javascript will parse
  // it by associating the index with a color
  // 
  public ArrayList<Integer[]> temps = new ArrayList<Integer[]>();
  public ArrayList<Integer> scores = new ArrayList<Integer>();
  public ArrayList<String> names = new ArrayList<String>();

  //still need to assign player color at this point
  public Game(ArrayList<Player> players, int gameNum){
    this.gameNum = gameNum;
    this.isOpen = true;
    for(int k = 0; k < 30; k ++){       //init colorGrid to all white
      for(int i = 0; i < 30; i ++){
          colorGrid[k][i] = 'W';
      }
    }
    this.players = players;
    for(Player p: players){
      p.gameNum =  gameNum;
    }

    //assign color to players
    //R(red), Y(yellow), B(blue), Z(grey)
    if(players.size() == 2){
      players.get(0).color = 'R';
      players.get(1).color = 'Y';

    }
    else if(players.size() == 3){
        players.get(0).color = 'R';
        players.get(1).color = 'Y';
        players.get(2).color = 'B';
          
    }
    else{
        players.get(0).color = 'R';
        players.get(1).color = 'Y';
        players.get(2).color = 'B';
        players.get(3).color = 'Z';
    }
      

  }

  public Game(){
    this.players = new ArrayList<Player>();


    this.isOpen = true;
    for(int i = 0; i < 30; i++){
      for(int j = 0; j < 30; j++){
        //Random r = new Random();
        //char c = (char)(r.nextInt(26) + 'a');
        //matrix[i][j] = c;   //no longer need this randomly created matrix
        colorGrid[i][j] = 'w';
      }
    }
    numPlayers = 0;
  }
  
  // Adds the players queued  //this might end up being unused
  public void addEntries(Player player){
    scores.add(player.score);
    names.add(player.name);
    Integer[] nulls = {-1,-1};
    temps.add(nulls);
    numPlayers++;

  }

  public void highlightCell(int playerIdx, int[] coord){
    Integer[] casted = new Integer[2];
    casted[0] = coord[0];
    casted[1] = coord[1];
    temps.set(playerIdx, casted);
  }

  public void highlightWord(int playerIdx, int[] startCoords, int[] endCoords){
    
  }

  public void playerSetReady(ArrayList<Player> playerList){
    // check number of ready players every time this thing is called, start the game immediately the moment it hits 2
    Lobby readyplayer = new Lobby(playerList);
      if(readyplayer.numReady == 2){
          startGame();
      }
    
  }

  public boolean checkWord(int[] startCoords, int[] endCoords){
    // parses coords, strings together word, checks if it's inside words used
    // Placeholder for testing purposes: randomly returns true or false
    return true;
  }


  public void startGame(){ 
  }

  public void endGame(){
  }
}//class's curly
