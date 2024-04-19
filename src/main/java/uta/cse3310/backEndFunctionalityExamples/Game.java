
import java.util.ArrayList;
import java.lang.StringBuilder;


public class Game {
    public int gameID;
    public boolean isOpen;
    public Matrix matrix;
    public char[][] colorGrid;      //grid colors corresponding to highlighted words
    public ArrayList<Player> players;   //list off all players
    public ArrayList<Words> wordsFound; //list of all words found

    //when game is started the 'Player' is
    //allready filled in, this takes in a set of 
    //players that decided to join a game
    Game(ArrayList<Player> players, int gameID){
        this.gameID = gameID;
        this.isOpen = true;
        this.matrix = new Matrix();         //load in our matrix
        this.colorGrid = new char[30][30];
        for(int k = 0; k < 30; k ++){       //init colorGrid to all white
            for(int i = 0; i < 30; i ++){
                colorGrid[k][i] = 'W';
            }
        }
        this.players = players;             //load in our players

       
        for(Player p: players){             //set proper gameID for all players
            p.gameID = gameID;
            
        }
        
        //assign color to players
        //R(red), Y(yellow), B(blue), Z(grey)
        if(players.size() == 2){
            players.get(0).color = 'R';
            players.get(1).color = 'Y';

        }
        else if(players.size() == 3){
            players.get(0).color = 'R';
            players.get(1).color = 'Y';
            players.get(2).color = 'B';
            
        }
        else{
            players.get(0).color = 'R';
            players.get(1).color = 'Y';
            players.get(2).color = 'B';
            players.get(3).color = 'Z';
        }
        


        this.wordsFound = new ArrayList<Words>();
    }

    //verifies word has not been found yet 
    //verifies word is within our grid
    //adds valid words to the 'wordsFound' list 
    //return true if valid,  false if not valid
    public boolean verifyWord(String word){

        //Alwasy have to check inverse of every word
        StringBuilder SBWord = new StringBuilder(word);
        SBWord.reverse();
        String inverseWord = SBWord.toString();


        //check if word has not been found yet
        if(wordsFound.isEmpty()){
            //proceed since word hasnt been found yet
        }
        else{
            for(Words w : wordsFound){
                if(w.word.equals(word) || w.word.equals(inverseWord)){
                    //word has been previously found
                    return false;
                }
            }
        }

        //look up the word within the grid
        if(matrix.wordLookUp(word) != null){    //returns a 'Words' object if found
            wordsFound.add(matrix.wordLookUp(word));
            return true;
        }

        return false;
    }

    //verify player belongs to this game
    //returns true if valid false if player doesnt belong
    public boolean verifyPlayer(Player player){
        //we can just check nicknames since they are all unique
        for(Player p: players){
            if(p.name.equals(player.name)){
                return true;    //player part of this game
            }
        }
        return false;   //player not part of this game
    }

    //highlights words that have been found and validated
    public void highlight(Words word, char color){
        //word.x_startPoint;
        //word.y_startPoint;
        //word.x_endPoint;
        //word.y_endPoint;

        if(word.x_startPoint ==word.x_endPoint){    //vertical inserted word
            int x = word.x_startPoint;
            for(int y = word.y_startPoint; y <= word.y_endPoint; y++){

                
                
                //choose appropriate color
                char c = colorMixer(colorGrid[y][x], color);
                //System.out.println(c);  //debugging
                colorGrid[y][x] = c;
                
            }
        }
        else if(word.y_startPoint == word.y_endPoint){  //horizontal inserted word
            int y = word.y_startPoint;
            for(int x = word.x_startPoint; x <= word.x_endPoint; x++){

                
                char c = colorMixer(colorGrid[y][x], color);
                colorGrid[y][x] = c;
                
            }
        }
        else if(word.x_startPoint < word.x_endPoint){   //TOP-LEFT -> BOTTOM-RIGHT
            int x = word.x_startPoint;  //increases
            int y = word.y_startPoint;  //increases
            //both x and y must move the same
            while(x<= word.x_endPoint && y<= word.y_endPoint){
                
                
                char c = colorMixer(colorGrid[y][x], color);
                colorGrid[y][x] = c;
                x++;
                y++;
                
            }
        }
        else if(word.x_startPoint > word.x_endPoint){   //TOP-RIGHT -> BOTTOM-RIGHT
            int x = word.x_startPoint;  //decreases
            int y = word.y_startPoint;  //increases
            //both x and y must move the same
            while(x>= word.x_endPoint && y<= word.y_endPoint){
                

                
                char c = colorMixer(colorGrid[y][x], color);
                colorGrid[y][x] = c;
                x--;
                y++;
                
            }
        }
        else{
            System.out.println("Orientation not found");
        }
    }

    //mixes Rred, Yyellow, Blue, Zgrey can handle any other colors
    public char colorMixer(char color1, char color2){
        //we have four original colors
        //R(red)                    //O(orange)
        //Y(yellow)                 //G(green)
        //B(blue)                   //V(violet)
        //Z(grey)                   //B(black ... dark grey)

        if(color1 == color2){   //same colors 
            return color1;
        }
        else if(color1 == 'W'){ //a white mix
            return color2;
        }
        else if(color2 == 'W'){ //a white mix
            return color1;
        }
        else if(color1 == 'Z' || color2 == 'Z'){    //grey color with anything else
            return 'B';
        }


        //no white colors no grey colors and no 'equal colors'
        switch(color1){
            case 'R':
                switch(color2){
                    case 'Y':
                        return 'O';
                    case 'B':
                        return 'V';
                    default:
                        return 'B';
                }
                
            case 'Y':
                switch(color2){
                    case 'R':
                        return 'O';
                    case 'B':
                        return 'G';
                    default:
                        return 'B';
                }
                
            case 'B':
                switch(color2){
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

    //word found by a player returns true if word is adds points to player
    public boolean playerFoundWord(Player p, String word){

        if(verifyPlayer(p)){
            //player was verified
        }
        else{
            return false;
        }

        if(verifyWord(word)){
            //word verified and added to 'wordsFound'
        }
        else{
            return false;
        }

        //edit score
        p.score += 1;
        //highlight cooresponding portion of the colorGrid
        Words w = wordsFound.get(wordsFound.size() - 1);
        highlight(w, p.color);
        

        return true;
    }

    //a player has exited the game
    public boolean playerExit(Player p){
        //make sure player belongs to this game
        if(verifyPlayer(p)){
            players.remove(p);
            return true;
        }
        else{
            return false;
        }

    }

    //display the scoreboard 
    public void printScoreBoard(){
        System.out.println("--------- Scoreboard ----------");
        for(Player p: players){
            System.out.println(p.name + " :  " + " " + p.score);
        }
    }

    public void printColorGrid(){
        System.out.println("\n----- Color Grid ----(W-white)(R-red)(Y-yellow)(Z-grey)(B- blue)(O-orange)(G-green)(V-violet)");
        for (int y = 0; y < matrix.numRows; y++) {
            for (int x = 0; x < matrix.numCols; x++) {
              
                System.out.print(colorGrid[y][x] + " ");
              
      
      
            }
            System.out.println();
        }


    }

    //check if the game has been won (if all words have been found)
    public boolean gameFinished(){
        if(wordsFound.size() == matrix.usedWordList.size()){
            System.out.println("Game Finished");
            printScoreBoard();
            this.matrix.printGrid();
            printColorGrid();

            return true;
        }
        else{
            return false;
        }
    }

}
