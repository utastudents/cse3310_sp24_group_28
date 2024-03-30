package com.example;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Timer;
public class Game {
  // Possible attributes needed
  // list of players
  public Matrix gameMatrix;
  public char[][] matrix = new char[30][30];
  //list of players inside each individual game
  LinkedHashMap<String, Integer> scoreList;
  public boolean isOpen;
  // Timer timer;

  public Game(){
    this.isOpen = true;
    this.gameMatrix = new Matrix();
    for(int i = 0; i < 30; i++){
      for(int j = 0; j < 30; j++){
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        matrix[i][j] = c;
      }
    }
    this.scoreList = new LinkedHashMap<String, Integer>();
  }
  
  // Adds the players queued
  public void addEntries(Player player){
    scoreList.put(player.name, player.score);
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
    return true;
  }


  public void startGame(){
  }

  public void endGame(){

  }
}//class's curly
