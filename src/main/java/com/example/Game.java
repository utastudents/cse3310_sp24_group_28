package com.example;

public class Game {
  // Possible attributes needed
  // list of players
  
  //list of players inside each individual game
  public Player playerList[];
  public boolean isOpen;
  Matrix gameMatrix;


  public Game(){
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
