package com.example;

public class Game {
  // Possible attributes needed
  // list of players
  int numRows;
  int numCols;
  public char[][] grid;
  // list of used nicknames shared between all the games?
  static String nicknames[];
  //list of players inside each individual game
  Player playerList[];

  public Game(){
  }

  public void initGrid(){
  }
  public void printGrid(){
  }

  public void placeWord(){
  }

  public void fillHori(String word, int row, int col){
  }
  public void fillVert(String word, int row, int col){
  }
  public void fillDiag(String word, int row, int col){
  }


  public void highlightCell(int playerID, int[] coord){

  }
  public void checkWord(int[] startCoords, int[] endCoords, char[][] grid){

  }

  public void playerSetReady(){

  }

  public void startGame(){

  }

  public void endGame(){

  }
}//class's curly
