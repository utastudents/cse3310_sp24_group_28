package com.example;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.lang.Boolean;

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

  public Matrix(String filename){
  }
  public Matrix(){
    for(int i = 0; i < numRows; i++){
      for(int j = 0; j < numCols; j++){
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        matrix[i][j] = c;
      }
    }
  }

  public void initGrid(){
    
  }
  
  
  
  public void horizontalWordInsert(boolean invert, String word, char[][] matrix){
  }
  public void verticalWordInsert(boolean invert, String word, char[][] matrix){
  }
  public void diagonalWordInsert(boolean invert, String word, char[][] matrix){
  }  
  
  public void selectWords(){
  }
  public void printGrid(){
  }
  public void displayStats(float randomness, float density, int fillerCharacters){}
  
  public int insertFillerChar(char[][] matrix){
    return 1;
  }

  public char[][] wordSearchMatrix(String filename){
    return matrix;
  }

  
  //TEST CASE BELOW:
  public void testMatrix(){
    char[][] grid = new char[50][50];
  
    List<String> wordBank = new ArrayList<String>();

    wordBank.add("Ant");
    wordBank.add("Zebra");
    wordBank.add("Baboon");

    //Matrix wordSearch = new Matrix(grid);

    //wordSearch.horizontalWordInsert(false,null, grid);

    //assertTrue(gridHasWords(grid, wordBank));
  }

  boolean gridHasWords(char[][] grid, List<String> wordBank){
    return false;
  }
  

}
