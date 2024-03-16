package com.example;

public class Game {
  // Possible attributes needed
  // list of players
  int numRows;
  int numCols;
  public char[][] grid;
  //list of players inside each individual game
  Player playerList[];
  boolean isOpen;

  public Game(){
  }

  public void initGrid(){
  }
  public void printGrid(){
  }

  public void fillGrid(){
  }

  public void fillHori(String word, int row, int col){
  }
  public void fillVert(String word, int row, int col){
  }
  public void fillDiag(String word, int row, int col){
  }


  public void chooseCell(int playerID, int[] coord){

  }

  public void checkWord(int[] startCoords, int[] endCoords, char[][] grid){

  }

  public void highlightWord(int[] startCoords, int[] endCoords){

  }

  public void playerSetReady(){
    // check number of ready players every time this thing is called, start the game immediately the moment it hits 2
    
  }

  public void startGame(){

  }

  public void endGame(){

  }
}//class's curly
