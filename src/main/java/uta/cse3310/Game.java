package uta.cse3310;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Timer;
import java.util.Iterator;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Game {
    public String stats;
    public int gameNum; // coorealates games and corresponding players
    public Matrix matrix = new Matrix();
    public ArrayList<Player> players; // players in this game can also get the 'numPlayers' easily
    public ArrayList<Words> wordsFound = new ArrayList<Words>(); // words found in the game
    public ArrayList<String> wordBank = matrix.wordBankList; // easily get a hold of the wordbank to display during a
                                                            // game

    // Possible attributes needed
    public boolean isOpen;
    public int numPlayers;
    public char[][] matrixDup = matrix.grid;
    public char[][] colorGrid = new char[30][30];
    // temps is a list of coordinate pairs. Javascript will parse
    // it by associating the index with a color
    //
    public ArrayList<Integer[]> temps = new ArrayList<Integer[]>();
    public ArrayList<Integer> scores = new ArrayList<Integer>();
    public ArrayList<String> names = new ArrayList<String>();

    // still need to assign player color at this point
    public Game(ArrayList<Player> players, int gameNum) {
        this.gameNum = gameNum;
        this.isOpen = true;
        for (int k = 0; k < 30; k++) { // init colorGrid to all white
            for (int i = 0; i < 30; i++) {
                colorGrid[k][i] = 'W';
            }
        }
        this.players = players;
        for (Player p : players) {
            p.gameNum = gameNum;
        }

    }

    public Game() {
        this.players = new ArrayList<Player>();
        this.isOpen = true;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                // Random r = new Random();
                // char c = (char)(r.nextInt(26) + 'a');
                // matrix[i][j] = c; //no longer need this randomly created matrix
                colorGrid[i][j] = 'W';
            }
        }
        numPlayers = 0;

    }

    // Adds the players queued //this might end up being unused
    public void addEntries(Player player) {
        scores.add(player.score);
        names.add(player.name);
        Integer[] nulls = { -1, -1 };
        temps.add(nulls);
        numPlayers++;

    }

  
    public void highlightCell(int playerIdx, int[] coord) {
        Integer[] casted = new Integer[2];
        casted[0] = coord[0];
        casted[1] = coord[1];
        temps.set(playerIdx, casted);
        System.out.println(temps.get(playerIdx)[0] + " " + temps.get(playerIdx)[1]);
    }

    // verifies word has not been found yet
    // verifies word is within our grid
    // adds valid words to the 'wordsFound' list
    // return true if valid, false if not valid
    public boolean verifyWord(String word) {

        // Alwasy have to check inverse of every word
        StringBuilder SBWord = new StringBuilder(word);
        SBWord.reverse();
        String inverseWord = SBWord.toString();

        // check if word has not been found yet
        if (wordsFound.isEmpty()) {
            // proceed since word hasnt been found yet
        } else {
            for (Words w : wordsFound) {
                if (w.word.equals(word) || w.word.equals(inverseWord)) {
                    // word has been previously found
                    return false;
                }
            }
        }

        // look up the word within the grid
        if (matrix.wordLookUp(word) != null) { // returns a 'Words' object if found
            wordsFound.add(matrix.wordLookUp(word));
            return true;
        }

        return false;
    }

    // public void updateWordBank(ArrayList<Words> wordsFound) {
    //     if (wordsFound == null) {
    //         // Does nothing here since there's no need to take action.
    //     }

    //     if (wordBank == null) {
    //         wordBank = new ArrayList<>();
    //     }

    //     Iterator<String> eraser = wordBank.iterator(); // Used to compare the words in wordBank to wordsFound and remove
    //                                                    // the matched word
    //     boolean remove = false; // Indicates if we need to have to remove a word

    //     // Checks the word bank to remove the words when they're chosen by a player
    //     while (eraser.hasNext()) {
    //         String bankWord = eraser.next();

    //         for (Words foundWord : wordsFound) {
    //             if (bankWord.equals(foundWord.word)) {
    //                 remove = true;
    //             }
    //         }

    //         if (remove) {
    //             eraser.remove();
    //         }
    //     }
    // }

    // verifies word has not been found yet
    // verifies word is within our grid
    // adds valid words to the 'wordsFound' list
    // removes word found from wordbank 
    // return true if valid, false if not valid
    public Words verifyWordCoords(int xStart, int yStart, int xEnd, int yEnd) {

        // Alwasy have to check inverse of every word

        // check if word has not been found yet
        if (wordsFound.isEmpty()) {
            // proceed since word hasnt been found yet
        } else {
            for (Words w : wordsFound) {

                int xS = w.x_startPoint;
                int yS = w.y_startPoint;
                int xE = w.x_endPoint;
                int yE = w.y_endPoint;
                // if coordinates match, then we've already found this word
                if (xStart == xS && xEnd == xE && yStart == yS && yEnd == yE) {
                    return null;
                    // word already found
                } else if (xStart == xE && xEnd == xS && yStart == yE && yEnd == yS) {
                    return null;
                }
            }
        }

        // look up the word within the grid
        // if(matrix.wordLookUp(word) != null){ //returns a 'Words' object if found
        // wordsFound.add(matrix.wordLookUp(word));
        // return true;
        // }

        // try and find the word picked by player within our grid
        // matrix.wordsUsed is the list of all words within our grid
        int index = 0;
        for (Words w : matrix.usedWordList) {

            int xS = w.x_startPoint;
            int yS = w.y_startPoint;
            int xE = w.x_endPoint;
            int yE = w.y_endPoint;
            
            if (xStart == xS && xEnd == xE && yStart == yS && yEnd == yE) {
                //we have found the word in our grid remove it from wordbank
                wordBank.set(index, "----"); //basically clears the index
                return w;

            } else if (xStart == xE && xEnd == xS && yStart == yE && yEnd == yS) {
                //we have found the word in our grid remove it from wordbank
                wordBank.set(index, "----"); //basically clears the index
                return w;
            }

            index++;

        }

        return null;
    }

    // verify player belongs to this game
    // returns true if valid false if player doesnt belong
    public boolean verifyPlayer(Player player) {
        // we can just check nicknames since they are all unique
        for (Player p : players) {
            if (p.name.equals(player.name)) {
                return true; // player part of this game
            }
        }
        return false; // player not part of this game
    }

    // highlights words that have been found and validated
    public void highlight(Words word, char color) {
        // word.x_startPoint;
        // word.y_startPoint;
        // word.x_endPoint;
        // word.y_endPoint;

        if (word.x_startPoint == word.x_endPoint) { // vertical inserted word
            int x = word.x_startPoint;
            for (int y = word.y_startPoint; y <= word.y_endPoint; y++) {

                // choose appropriate color
                char c = colorMixer(colorGrid[y][x], color);
                // System.out.println(c); //debugging
                colorGrid[y][x] = c;

            }
        } else if (word.y_startPoint == word.y_endPoint) { // horizontal inserted word
            int y = word.y_startPoint;
            for (int x = word.x_startPoint; x <= word.x_endPoint; x++) {

                char c = colorMixer(colorGrid[y][x], color);
                colorGrid[y][x] = c;

            }
        } else if (word.x_startPoint < word.x_endPoint) { // TOP-LEFT -> BOTTOM-RIGHT
            int x = word.x_startPoint; // increases
            int y = word.y_startPoint; // increases
            // both x and y must move the same
            while (x <= word.x_endPoint && y <= word.y_endPoint) {

                char c = colorMixer(colorGrid[y][x], color);
                colorGrid[y][x] = c;
                x++;
                y++;

            }
        } else if (word.x_startPoint > word.x_endPoint) { // TOP-RIGHT -> BOTTOM-RIGHT
            int x = word.x_startPoint; // decreases
            int y = word.y_startPoint; // increases
            // both x and y must move the same
            while (x >= word.x_endPoint && y <= word.y_endPoint) {

                char c = colorMixer(colorGrid[y][x], color);
                colorGrid[y][x] = c;
                x--;
                y++;

            }
        } else {
            System.out.println("Orientation not found");
        }
    }

    // mixes Rred, Yyellow, Blue, Zgrey can handle any other colors
    public char colorMixer(char color1, char color2) {
        // we have four original colors
        // R(red) //O(orange)
        // Y(yellow) //G(green)
        // B(blue) //V(violet)
        // Z(grey) //B(black ... dark grey)

        if (color1 == color2) { // same colors
            return color1;
        } else if (color1 == 'W') { // a white mix
            return color2;
        } else if (color2 == 'W') { // a white mix
            return color1;
        } else if (color1 == 'Z' || color2 == 'Z') { // grey color with anything else
            return 'B';
        }

        // no white colors no grey colors and no 'equal colors'
        switch (color1) {
            case 'R':
                switch (color2) {
                    case 'Y':
                        return 'O';
                    case 'B':
                        return 'V';
                    default:
                        return 'B';
                }

            case 'Y':
                switch (color2) {
                    case 'R':
                        return 'O';
                    case 'B':
                        return 'G';
                    default:
                        return 'B';
                }

            case 'B':
                switch (color2) {
                    case 'R':
                        return 'V';
                    case 'Y':
                        return 'G';
                    default:
                        return 'B';
                }

            default:
                return 'B';

        }

    }

    // word found by a player returns true if word is adds points to player
    public boolean playerFoundWord(int[] startCoords, int[] endCoords) {
        System.out.println(Arrays.toString(startCoords));
        System.out.println(Arrays.toString(endCoords));
        if (verifyWordCoords(startCoords[1], startCoords[0], endCoords[1], endCoords[0]) != null) {
            wordsFound.add(verifyWordCoords(startCoords[1], startCoords[0], endCoords[1], endCoords[0]));
            // word verified and added to 'wordsFound'
        } else {
            return false;
        }
        // go from coord to figureing out if the word is valid
        // recreate word validation via coordinates

        // int[] startCoords = [yS, xS]
        // int[] endCoords = [yE, xE]
        // Thanh int xS = startCoords[1];

        // words xS, yS, xE, yE
        //
        // edit score
        // p.score += 1; // possible change here --------------------------
        // highlight cooresponding portion of the colorGrid
        // Words w = wordsFound.get(wordsFound.size() - 1);
        // highlight(w, 'W');

        return true;
    }

    // a player has exited the game
    public boolean playerExit(Player p) {
        // make sure player belongs to this game
        if (verifyPlayer(p)) {
            players.remove(p);
            return true;
        } else {
            return false;
        }

    }

    // display the scoreboard
    public void printScoreBoard() {
        System.out.println("--------- Scoreboard ----------");
        for (Player p : players) {
            System.out.println(p.name + " :  " + " " + p.score);
        }
    }

    public void printColorGrid() {
        System.out.println(
                "\n----- Color Grid ----(W-white)(R-red)(Y-yellow)(Z-grey)(B- blue)(O-orange)(G-green)(V-violet)");
        for (int y = 0; y < matrix.numRows; y++) {
            for (int x = 0; x < matrix.numCols; x++) {

                System.out.print(colorGrid[y][x] + " ");

            }
            System.out.println();
        }

    }

    // check if the game has been won (if all words have been found)
    public boolean gameFinished() {
        if (wordsFound.size() == matrix.usedWordList.size()) {
            System.out.println("Game Finished");
            printScoreBoard();
            this.matrix.printGrid();
            printColorGrid();

            return true;
        } else {
            return false;
        }
    }

    public void endGame() {
    }
}// class's curly
