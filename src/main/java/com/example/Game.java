package com.example;

public class Game {
  // Possible attributes needed
  // list of players
  int numRows;
  int numCols;
  String[] wordList = {"CAT", "APPLE", "SWEATER", "HELLO",
                         "WORLD", "CAR", "DISTRESS", "YELLOW",
                         "WOMAN", "HANDS", "SEESAW", "LARD"};
  public char[][] grid;
  
  public Game(){
    grid = new char[30][30];

    numRows = 30;
    numCols = 30;
  }

  public void initGrid(){
    for(int i = 0; i < numRows; i++){
      for(int j = 0; j < numCols; j++){
        grid[i][j] = ' ';
      }
    }
  }
  public void printGrid(){

    for(int i = 0; i < numRows; i++){
      System.out.printf("%d ", i);
      for(int j = 0; j < numCols; j++){
        System.out.printf("| %c ", grid[i][j]);
      }
      System.out.printf("|");
      System.out.printf("\n-------------------------------------------\n");
    }
  }

  public void placeWord(){
    char[] valids = {'h', 'v', 'd'};
    
    // Choose random direction to fill towards.
    int dir = (int)Math.floor(Math.random() * 2);
    //   int col = (int)Math.floor(Math.random() * numCols);
    //   String word = wordList[(int)Math.floor(Math.random() * 12)];
    
    fillHori("ORANGE", 3, 2);

    fillVert("APPLE", 4, 0);
    fillVert("APPLE", 6, 1);

    fillVert("APPLE",8,2);
    fillVert("APPLE",7,3);
  
    fillVert("APPLE",3,4);
    fillVert("APPLE",7,7);
    fillVert("WRONGS",7,6);
  }

  public void fillHori(String word, int row, int col){
    // System.out.printf("Start: [%d][%d]\n", row, col);
    // System.out.printf("%s: ",word);
    int len = word.length();
    boolean free = true;
    boolean filled = false;

    if(grid[row][col] == ' ' || grid[row][col] == word.charAt(0)){
      //looking right
      //NUMROWS -1 - 4 = 5
      if(numRows-col >= len && filled == false){
        // System.out.println("enough space right.");
        //check the length of the space if any cells are occupied.
        for(int i = 0; i < len; i++){
          if(grid[row][i+col] != ' ' && grid[row][i+col] != word.charAt(i)){
            // System.out.printf("[%d][%d] not empty.\n", row, col+i);
            free = false;
            break;
          }
        }
        if(free == true){
          for(int i = 0; i < len; i++){
            grid[row][i+col] = word.charAt(i);
          }
          filled = true;
        }
      }

      //looking left
      if(col + 1 >= len && filled == false){
        // System.out.println("enough space left.");
        free = true;
        for(int i = 0; i < len; i++){
          if(grid[row][col-i] != ' ' && grid[row][col-i] != word.charAt(i)){
            // System.out.printf("[%d][%d] not empty.\n", row, col-i);
            free = false;
          }
        }
        if(free == true){
          for(int i = 0; i < len; i++){
            grid[row][col-i] = word.charAt(i);
          }
          filled = true;
        }
      }
      else if(filled == false){
        // System.out.println("No space left or right.");
      }
    }
    else{
      // System.out.println("Coord Occupied.");
    }
  }

  public void fillVert(String word, int row, int col){
    // System.out.printf("Start: [%d][%d]\n", row, col);
    // System.out.printf("%s: ",word);
    int len = word.length();
    boolean free = true;
    boolean filled = false;

    if(grid[row][col] == ' ' || grid[row][col] == word.charAt(0)){
      //looking down
      if(numRows-row >= len && filled == false){
        // System.out.println("enough space down.");
        //check the length of the space if any cells are occupied.
        for(int i = 0; i < len; i++){
          if(grid[i+row][col] != ' ' && grid[i+row][col] != word.charAt(i)){
            // System.out.printf("[%d][%d] not empty.\n", row+i, col);
            free = false;
            break;
          }
        }
        if(free == true){
          for(int i = 0; i < len; i++){
            grid[i+row][col] = word.charAt(i);
          }
          filled = true;
        }
      }

      //looking up
      if(row + 1 >= len && filled == false){
        // System.out.println("enough space up.");
        free = true;
        for(int i = 0; i < len; i++){
          if(grid[row-i][col] != ' ' && grid[row-i][col] != word.charAt(i)){
            // System.out.printf("[%d][%d] not empty.\n", row-i, col);
            free = false;
          }
        }
        if(free == true){
          for(int i = 0; i < len; i++){
            grid[row-i][col] = word.charAt(i);
          }
          filled = true;
        }
      }
      else if(filled == false){
        // System.out.println("No space up or down.");
      }
    }
    else{
      // System.out.println("Coord Occupied.");
    }
  }
}//class's curly
