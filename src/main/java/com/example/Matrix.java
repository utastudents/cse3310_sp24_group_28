package com.example;

public class Matrix {
  public float density;
  public String words[];
  public int numRows;
  public int numCols;
  float randomness;
  int fillerCharacter;
  public char[][] matrix;
  char[] fillerCharacters;
  public String wordsUsed[];

  Matrix(String filename){

  }

  public void displayStats(float randomness, float density, int fillerCharacters){}
  
  public int insertFillerChar(char[][] matrix){
    return 1;
  }

  public char[][] wordSearchMatrix(String filename){
    return matrix;
  }
  
  public void selectWords(){
  }

  public void initGrid(){
  }



  public void horizontalWordInsert(boolean invert, String word, char[][] matrix){
  }
  public void verticalWordInsert(boolean invert, String word, char[][] matrix){
  }
  public void diagonalWordInsert(boolean invert, String word, char[][] matrix){
  }  
  
  
  public void printGrid(){
  }
}
