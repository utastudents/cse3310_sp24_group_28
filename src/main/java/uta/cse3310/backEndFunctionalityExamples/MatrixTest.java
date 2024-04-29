package uta.cse3310.backEndFunctionalityExamples;
public class MatrixTest {
    

    public static void main(String[] args){

        Matrix myMatrix = new Matrix();
        myMatrix.printUsedWordList();
        System.out.println("Shared letters: " + myMatrix.sharedLetterCount);
    }
}
