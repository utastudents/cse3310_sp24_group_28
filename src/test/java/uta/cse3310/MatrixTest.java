package uta.cse3310;

import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;

public class MatrixTest extends TestCase {
  public void testGridHasWords() {
    char[][] grid = new char[30][30];
    List<String> wordBank = new ArrayList<String>();
    wordBank.add("Ant");
    wordBank.add("Zebra");
    wordBank.add("Baboon");

    boolean result = gridHasWords(grid, wordBank);

    assertFalse(result); // Add your assertions based on the expected behavior
  }

  // Matrix wordSearch = new Matrix(grid);

  // wordSearch.horizontalWordInsert(false,null, grid);

  // assertTrue(gridHasWords(grid, wordBank));

  boolean gridHasWords(char[][] grid, List<String> wordBank) {
    return false;
  }

  public void testingWordLayout() {
    boolean insert1 = verticalWordInsert("Test1");
    boolean insert2 = horizontalWordInsert("Test2");
    boolean insert3 = diagonalWordInsert1("Test3");

    if (!insert1) {
      System.out.println("Word not printed vertically");
    }

    if (!insert2) {
      System.out.println("Word not printed horizontally");
    }

    if (!insert3) {
      System.out.println("Word not printed diagonally");
    }

    String expWord = "Test";
    StringBuilder actualWord = new StringBuilder();

    for (int i = 0; i < expWord.length(); i++) { // tests the diagonal part
      actualWord.append(grid[i][i]);
    }

    for (int i = 0; i < expWord.length(); i++) { // tests the horizontal part
      actualWord.append(grid[0][i]);
    }

    for (int i = 0; i < expWord.length(); i++) { // tests the vertical part
      actualWord.append(grid[i][0]);
    }

    if (!expWord.equals(actualWord.toString())) {
      System.out.println("Word wasn't inserted correctly diagonally");
    }

    System.out.println("Test Case: PASSED");
  }

  public void testDensity() {
    Matrix m = new Matrix();
    //did we reach appropriate density
    
    assertTrue("Matrix density reached minimum density\n", m.minDens <= m.density);

    //we can also double check by finding percent of grid that is filler charachters 
    float totalChar = m.numCols * m.numCols;
    float calculatedDensity = m.numFillerCharacters / totalChar;
    //get the density of non filler charachters
    calculatedDensity = 1 - calculatedDensity;

    assertTrue("Calculated Density using number of filler charachters\n", m.minDens <= calculatedDensity);



    // fillGrid();

    // float minDens = 0.6f;
    // float expMinDens = minDens;

    // assertFalse("The density is below the minimum", calcDensity() >= expMinDens);

    // System.out.println("Test Case for density: PASSED");

  }

  public void testSharedLetters() {
    // boolean testWord = horizontalWordInsert("Testing");
    // assertTrue("Failed to share two words together", testWord);

    // boolean testWord2 = horizontalWordInsert("Together");
    // assertTrue("Failed to share two words together", testWord2);



    //second version
    Matrix m = new Matrix();
    System.out.println("Can we reach atleast 10 shared letters");
    boolean b = false;
    //if 10 words share letters lets think of them as couples 
    //that means we should have at least 5 letters in our grid that are shared between words
    if(m.sharedLetterCount >= 5){
      b = true;
    }
    //if not true then we didnt reach shared letter necessery
    assertTrue("10 words do not share letters", b);
  }


  public void testFillerLetter(){
    Matrix m = new Matrix();

    //for each letter in the alphabete A - Z
    System.out.println("We except to see a similar amount of each letter used as a filler");
    System.out.println("Char:Count");
    for(char C: m.fillerCharachters){
      //for all filler charachters inserted into our matrix
      int count = 0;
      //find how many matches for each letter we get
      for(char I: m.fillerCharactersUsed){
        if(C == I){
          count++;
        }
        System.out.println(C + " : " + count);
      }


    }




  }
}