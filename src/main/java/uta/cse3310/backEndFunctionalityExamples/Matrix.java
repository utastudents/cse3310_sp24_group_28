package uta.cse3310.backEndFunctionalityExamples;
import java.lang.StringBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;


//this class is based around a 50 x 50 grid 
//everything else is just to keep track of this grid and the data withing
public class Matrix {
  
  public int sharedLetterCount;         //keeps count of how many letters are shared by two words

  public float density;                  //percent of letters used for words (1.00 == every letter belongs to a word)
  public ArrayList<String> wordList;     //a list of all the words available (loaded from a file)
  public ArrayList<Words> usedWordList;  //a list of all the words used/inserted in the grid
  public int numRows;                    //number of rows in grid
  public int numCols;                    //number of columns in grid
  public float randomness;               // still not sure what this is supposed to hold
  public int numFillerCharacters;        //number of charachters used to fill in empty spaces in the grid
  public char[][] grid;                  //the grid of words itsefl 
  public ArrayList<Character> fillerCharachters;   //a list of ALL possible filler charachters aka alphabette
  

  //non-default constructor
  Matrix(String filename){
  }

  //default constructor //HARDCODED FILE TO READ FROM
  Matrix(){

    sharedLetterCount = 0;

    //initiate all values to a default
    density = 0;

    wordList = new ArrayList<String>();
    initWordsList("wordlist_copy.txt");
    //printWordList();  //debugging

    usedWordList = new ArrayList<Words>();
    numRows = 30;
    numCols = 30;
    randomness = 0;
    numFillerCharacters = 0;

    grid = new char[numRows][numCols];
    initGrid(); 
    //printGrid();  //debugging

    fillerCharachters = new ArrayList<Character>();
    initFillerCharacters();
    //printFillerCharacters();  //debugging

    fillGrid();
    printGrid();  //prints grid before filler charachters inserted
    numFillerCharacters = insertFillerChar();
    printGrid();    //prints completed grid //debugging
    //printUsedWordList();  //debugging

  }

  //initialize our grid to all zeros only to be used at the start
  public void initGrid(){
    for(int k = 0; k < numRows; k ++){
      for(int i = 0; i < numCols; i ++){
        grid[k][i] = '0';
      }
    }
  }
  
  //initialize the list of possible filler charachters  (capitalized alphabete)
  public void initFillerCharacters(){
    for (char ch = 'a'; ch <= 'z'; ch++) {
      fillerCharachters.add(ch);
    }
  }

  //fill in our wordList array with all possible words (loaded from a file)
  public void initWordsList(String filename){
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String currentWord;
      while((currentWord = reader.readLine()) != null){
        currentWord = currentWord.trim();
        //only keep words with a minimum length of 4
        if(currentWord.length() >= 4){
          wordList.add(currentWord);
        }
      }
    }catch (IOException e){
      e.printStackTrace();
    }
  }

  //select a random word from our list of words (wordList)
  public String selectRandomWord(){
    if(wordList.isEmpty()){
      return null;
    }

    int bound = wordList.size();
    Random random = new Random();
    int index = random.nextInt(bound);

    return wordList.get(index);
  }

  //fills up grid with words in all orinetations
  public void fillGrid(){

    //fill grid with horizontal words -- lets try this first
    while(calcDensity() < 0.1){
      //insert one random word
      String word = selectRandomWord();
      Random rand = new Random();

      //System.out.println("Word to be inserted: " + word); //debugging

      int orientation = rand.nextInt(2);
      if(orientation == 0){  //regular word orietation
        horizontalWordInsert(word);
      }else{
        //invert our word
        StringBuilder SBWord = new StringBuilder(word);
        SBWord.reverse();
        String inverseWord = SBWord.toString();
        horizontalWordInsert(inverseWord);
      }
    }
    //System.out.println(calcDensity());  //debugging

    //fill grid with vertical words
    while(calcDensity() < 0.2){
      //insert one random word
      String word = selectRandomWord();
      Random rand = new Random();

      //System.out.println("Word to be inserted: " + word);   //debugging

      int orientation = rand.nextInt(2);
      if(orientation == 0){  //regular word orietation
        verticalWordInsert(word);
      }else{
        //invert our word
        StringBuilder SBWord = new StringBuilder(word);
        SBWord.reverse();
        String inverseWord = SBWord.toString();
        verticalWordInsert(inverseWord);
      }
    }
    //System.out.println(calcDensity());  //debugging

    //fill grid with diagonal words version 1 (top-left - > bottom-right)
    while(calcDensity() < 0.3){
      //insert one random word
      String word = selectRandomWord();
      Random rand = new Random();

      //System.out.println("Word to be inserted: " + word); // debugging

      int orientation = rand.nextInt(2);
      if(orientation == 0){  //regular word orietation
        diagonalWordInsert1(word);
      }else{
        //invert our word
        StringBuilder SBWord = new StringBuilder(word);
        SBWord.reverse();
        String inverseWord = SBWord.toString();
        diagonalWordInsert1(inverseWord);
      }
    }
    //System.out.println(calcDensity());  //debugging

    //fill grid with diagonal words version 2 (top-right -> bottom-left)
    while(calcDensity() < 0.4){
      //insert one random word
      String word = selectRandomWord();
      Random rand = new Random();

      //System.out.println("Word to be inserted: " + word); //debugging

      int orientation = rand.nextInt(2);
      if(orientation == 0){  //regular word orietation
        diagonalWordInsert2(word);
      }else{
        //invert our word
        StringBuilder SBWord = new StringBuilder(word);
        SBWord.reverse();
        String inverseWord = SBWord.toString();
        diagonalWordInsert2(inverseWord);
      }
    }
    //System.out.println(calcDensity());  //debugging

  }
  
  //inserts horizontal words also saves inserted words
  public void horizontalWordInsert(String word){

    //System.out.println("Word Recieved: " + word); //debugging

    //select a random spot in the 50x50 grid to start the insert
    Random r = new Random();
    boolean fit = false;
    int maxAttempts = numCols * numRows;
    maxAttempts = maxAttempts * 2;

    //will attempt to insert word at random start coordinates (x , y)
    //stops when it runs out of attempts
    //stops when word fits
    while(!fit && maxAttempts != 0){  

      maxAttempts--;
      char[] letters = word.toCharArray();
      int x = r.nextInt(numCols);
      int y = r.nextInt(numRows);
      //System.out.println("Coordinate attempted: " + x + " " +  y);  //debugging

      //check if it physically fits
      //we are horizontal so just in the X-direction
      int x_endpoint = x + word.length() - 1;


      if(x_endpoint < numCols){ //must be within grid otherwise loop again
        //check placement of each charachter before insert
        int curr = 0;
        boolean crash = false;  //checks if a charachter crashes with another charachter


        for(int k = x; k <= x_endpoint; k ++){  

          if(grid[y][k] == '0' || grid[y][k] == letters[curr]){
            
          }
          else{
            crash = true;
          }

          curr ++;
        }
        //once we check all charachters fitment 
        if(!crash){ //if we didnt crash exit the loop // otherwise loop again
          fit = true;
          curr = 0;

          for(int k = x; k <= x_endpoint; k ++){  
            if(grid[y][k] == letters[curr]){  //shared letters
              sharedLetterCount++;
            }
            grid[y][k] = letters[curr];
            curr ++;
          }
          Words wrd = new Words(word, x, y, x_endpoint, y);
          usedWordList.add(wrd);
        }
      }
    }
  }

  //inserts vertical words  also saves inserted words
  public void verticalWordInsert(String word){

    //System.out.println("Word Recieved: " + word);   //debugging

    //select a random spot in the 50x50 grid to start the insert
    Random r = new Random();
    boolean fit = false;
    int maxAttempts = numCols * numRows;
    maxAttempts = maxAttempts * 2;

    //will attempt to insert word at random start coordinates (x , y)
    //stops when it runs out of attempts
    //stops when word fits
    while(!fit && maxAttempts != 0){  

      maxAttempts--;
      char[] letters = word.toCharArray();
      int x = r.nextInt(numCols);
      int y = r.nextInt(numRows);
      //System.out.println("Coordinate attempted: " + x + " " +  y);    //debugging

      //check if it physically fits
      //we are horizontal so just in the X-direction
      //WRITE TOP DOWN
      int y_endpoint = y + word.length() - 1;


      if(y_endpoint < numRows){ //must be within grid otherwise loop again
        //check placement of each charachter before insert
        int curr = 0;
        boolean crash = false;  //checks if a charachter crashes with another charachter


        for(int k = y; k <= y_endpoint; k ++){  

          if(grid[k][x] == '0' || grid[k][x] == letters[curr]){
            
          }
          else{
            crash = true;
          }

          curr ++;
        }
        //once we check all charachters fitment 
        if(!crash){ //if we didnt crash exit the loop // otherwise loop again
          fit = true;
          curr = 0;

          for(int k = y; k <= y_endpoint; k ++){  
            if(grid[k][x] == letters[curr]){
              sharedLetterCount++;
            }
            grid[k][x] = letters[curr];
            curr ++;
          }
          Words wrd = new Words(word, x, y, x, y_endpoint);
          usedWordList.add(wrd);
        }
      }
    }

  }

  //inserts diagonal words TOP-LEFT -> BOTTOM-RIGHT also saves inserted words
  public void diagonalWordInsert1(String word){

    //System.out.println("Word Recieved: " + word); //debugging

    //select a random spot in the 50x50 grid to start the insert
    Random r = new Random();
    boolean fit = false;
    int maxAttempts = numCols * numRows;
    maxAttempts = maxAttempts * 2;

    //will attempt to insert word at random start coordinates (x , y)
    //stops when it runs out of attempts
    //stops when word fits
    while(!fit && maxAttempts != 0){  

      maxAttempts--;
      char[] letters = word.toCharArray();
      int x = r.nextInt(numCols);
      int y = r.nextInt(numRows);
      //System.out.println("Coordinate attempted: " + x + " " +  y);  //debugging

      //check if it physically fits
      //we are horizontal so just in the X-direction
      //WRITE TOP-LEFT -> BOTTOM-RIGHT
      int y_endpoint = y + word.length() - 1;
      int x_endpoint = x + word.length() - 1;


      if(y_endpoint < numRows && x_endpoint < numCols){ //must be within grid otherwise loop again
        //check placement of each charachter before insert
        int curr = 0;
        boolean crash = false;  //checks if a charachter crashes with another charachter


        for(int k = 0; k < word.length(); k ++){  

          if(grid[y + k][x + k] == '0' || grid[y + k][x + k] == letters[curr]){
            //letter is valid
          }
          else{
            crash = true;
          }

          curr ++;
        }
        //once we check all charachters fitment 
        if(!crash){ //if we didnt crash exit the loop // otherwise loop again
          fit = true;
          curr = 0;

          for(int k = 0; k < word.length(); k ++){  
            if(grid[y + k][x + k] == letters[curr]){
              sharedLetterCount++;
            }
            grid[y + k][x + k] = letters[curr];
            curr ++;
          }
          Words wrd = new Words(word, x, y, x_endpoint, y_endpoint);
          usedWordList.add(wrd);

        }
      }
    }


  }  

  //inserts diagonal words TOP-RIGHT -> BOTTOM-LEFT also saves inserted words
  public void diagonalWordInsert2(String word){
    //System.out.println("Word Recieved: " + word); //debugging

    //select a random spot in the 50x50 grid to start the insert
    Random r = new Random();
    boolean fit = false;
    int maxAttempts = numCols * numRows;
    maxAttempts = maxAttempts * 2;

    //will attempt to insert word at random start coordinates (x , y)
    //stops when it runs out of attempts
    //stops when word fits
    while(!fit && maxAttempts != 0){  

      maxAttempts--;
      char[] letters = word.toCharArray();
      int x = r.nextInt(numCols);
      int y = r.nextInt(numRows);
      //System.out.println("Coordinate attempted: " + x + " " +  y);  //debugging

      //check if it physically fits
      //we are horizontal so just in the X-direction
      //WRITE TOP-LEFT -> BOTTOM-RIGHT
      int y_endpoint = y + word.length() - 1;
      int x_endpoint = x - word.length() + 1;


      if(y_endpoint < numRows && x_endpoint >= 0){ //must be within grid otherwise loop again
        //check placement of each charachter before insert
        int curr = 0;
        boolean crash = false;  //checks if a charachter crashes with another charachter


        for(int k = 0; k < word.length(); k ++){  

          if(grid[y + k][x - k] == '0' || grid[y + k][x - k] == letters[curr]){
            
            
          }
          else{
            crash = true;
          }

          curr ++;
        }
        //once we check all charachters fitment 
        if(!crash){ //if we didnt crash exit the loop // otherwise loop again
          fit = true;
          curr = 0;

          for(int k = 0; k < word.length(); k ++){  
            if(grid[y + k][x - k] == letters[curr]){
              sharedLetterCount++;
            }
            grid[y + k][x - k] = letters[curr];
            curr ++;
          }
          Words wrd = new Words(word, x, y, x_endpoint, y_endpoint);
          usedWordList.add(wrd);
        }
      }
    }


  }

  //places filler charachters in empty locations returns number of inserts
  public int insertFillerChar(){
    //grab a random letter
    int inserts = 0;
    Random rand = new Random();
    int r;
    for(int y = 0; y < numCols; y ++){
      for(int x = 0; x < numRows; x ++){
        
        if(grid[y][x] == '0'){
          //chose a random charachter to insert
          r = rand.nextInt(fillerCharachters.size());
          char ch = fillerCharachters.get(r);
          grid[y][x] = ch;
          inserts ++;

        }
      }
    }
    return inserts;
  }

  //calculates density of grid BEFORE adding filler charachters
  public float calcDensity(){

    //find how many charachters we could have
    float size = numRows * numCols;
    float numChar = 0;
    for(int k = 0; k < numRows; k ++){
      for(int i = 0; i < numCols; i ++){
        char ch = grid[k][i];
        if(ch != '0'){
          numChar += 1;
        }
      }
    }
    if(numChar == 0){
      return 0;
    }
    else{
      return numChar / size;
    }

  }
  
  //prints grid in its current state
  public void printGrid(){

    System.out.println("---------- Word Grid ---------");
    for (int y = 0; y < numRows; y++) {
      for (int x = 0; x < numCols; x++) {
        //System.out.print(grid[y][x] + " ");
        if(grid[y][x] == '0'){
          System.out.print(" " + " ");
        }
        else{
          System.out.print(grid[y][x] + " ");
        }


      }
      System.out.println();
    }
  }

  //prints the full FillerCharachter List
  public void printFillerCharacters(){
    for(char ch : fillerCharachters){
      System.out.print(ch + " ");
    }
    System.out.println();
  }

  //prints the full wordList, list of all possible words from a file 
  public void printWordList(){
    for(String word : wordList){
      System.out.println(word);
    }
  }

  //prints the list of words used in our grid and their loaction
  public void printUsedWordList(){
    
    for(Words wrd : usedWordList){
      System.out.println(wrd.word + ": " + "(" + wrd.x_startPoint + ", " + wrd.y_startPoint + ") , (" + wrd.x_endPoint + ", " + wrd.y_endPoint + ") ");
    }
  }

  //returns 'Words' structure if string word is found within grid
  public Words wordLookUp(String word){

    //check the word in 'Inverted' fashion
    StringBuilder SBWord = new StringBuilder(word);
    SBWord.reverse();
    String inverseWord = SBWord.toString();
   

    for(Words w : usedWordList){
      if(w.word.equals(word) || w.word.equals(inverseWord)){
        return w;
      }
      
    }

    return null;
  }


  //TEST CASE BELOW:
  /* 
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
  */
}
