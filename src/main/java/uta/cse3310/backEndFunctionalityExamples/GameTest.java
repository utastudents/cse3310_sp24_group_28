import java.util.ArrayList;


public class GameTest {



    public static void main(String[] args){


        Player p1 = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        Player p4 = new Player("p4");
        

        ArrayList<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);

        //pass in an array of players ready to play
        Game game = new Game(players, 0);

        //for this test just continue finding words until the game is won


        //only use things through GAME dont acces players or anything else
        int index = 0;
        boolean gameStatus = false;
        while(!gameStatus){    //loop until the game is finsihed
            for(Player p: players){ //let each player find a word
                //chose a word to find automatically
                //get the next word 
                Words word = game.matrix.usedWordList.get(index);
                //get the word in string format
                //System.out.println(word.word);    //debugging
                String wordFound = word.word;
                index ++;


                //can be edited to work with coordinates instead of string words 
                game.playerFoundWord(p, wordFound); //tell the game a player possibly found a word
                if(game.gameFinished()){
                    gameStatus = true;
                    break;
                }


                

            }
        }

        //when game is done we can transfer players back to the lobby
        //remove their color
        //remove their gameID
        //list them as 'NOT READY'

        
        
    
        }
}
