package uta.cse3310;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Timer;
public class Game {
  // Possible attributes needed
  public boolean isOpen;
  public int numPlayers;
  public char[][] matrix = new char[30][30];
  public char[][] colorGrid = new char[30][30];
  // temps is a list of coordinate pairs. Javascript will parse
  // it by associating the index with a color
  // 
  public ArrayList<Integer[]> temps = new ArrayList<Integer[]>();
  public ArrayList<Integer> scores = new ArrayList<Integer>();
  public ArrayList<String> names = new ArrayList<String>();
  public Game(){
    this.isOpen = true;
    for(int i = 0; i < 30; i++){
      for(int j = 0; j < 30; j++){
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        matrix[i][j] = c;
        colorGrid[i][j] = 'w';
      }
    }
    numPlayers = 0;
  }
  
  // Adds the players queued
  public void addEntries(Player player){
    scores.add(player.score);
    names.add(player.name);
    Integer[] nulls = {-1,-1};
    temps.add(nulls);
    numPlayers++;

  }

  public void chooseCell(int playerIdx, int[] coord){

  }

  public void highlightWord(int playerIdx, int[] startCoords, int[] endCoords){

  }

  public void playerSetReady(){
    // check number of ready players every time this thing is called, start the game immediately the moment it hits 2

  }

  public boolean checkWord(int[] startCoords, int[] endCoords){
    // parses coords, strings together word, checks if it's inside words used
    // Placeholder for testing purposes: randomly returns true or false
    Random rand = new Random();
    int num = rand.nextInt(10);
    return true;
  }


  public void startGame(){
  }

  public void endGame(){

  }
}//class's curly