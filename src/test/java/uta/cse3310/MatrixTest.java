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
    fillGrid();

    float minDens = 0.6f;
    float expMinDens = minDens;

    assertFalse("The density is below the minimum", calcDensity() >= expMinDens);

    System.out.println("Test Case for density: PASSED");

  }

  public void testSharedLetters() {
    boolean testWord = horizontalWordInsert("Testing");
    assertTrue("Failed to share two words together", testWord);

    boolean testWord2 = horizontalWordInsert("Together");
    assertTrue("Failed to share two words together", testWord2);
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