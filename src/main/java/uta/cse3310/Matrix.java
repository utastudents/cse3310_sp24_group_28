package uta.cse3310;

import java.util.Random;

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
}