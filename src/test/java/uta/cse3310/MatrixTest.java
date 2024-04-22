package uta.cse3310;
import junit.framework.TestCase;
import java.util.List;
import java.util.ArrayList;

public class MatrixTest extends TestCase
{
    public void testGridHasWords() {
        char[][] grid = new char[50][50];
        List<String> wordBank = new ArrayList<String>();
        wordBank.add("Ant");
        wordBank.add("Zebra");
        wordBank.add("Baboon");

        boolean result = gridHasWords(grid, wordBank);
        
        assertFalse(result); // Add your assertions based on the expected behavior
    }


    //Matrix wordSearch = new Matrix(grid);

    //wordSearch.horizontalWordInsert(false,null, grid);

    //assertTrue(gridHasWords(grid, wordBank));
  

  boolean gridHasWords(char[][] grid, List<String> wordBank){
    return false;
  }
  
}