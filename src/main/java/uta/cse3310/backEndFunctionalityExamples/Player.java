package uta.cse3310.backEndFunctionalityExamples;
public class Player {
    public String name;
    public int score;
    public int index;
    public int gameID;  //same as gameNum
    public boolean isReady;
    public char color;
    

    Player(String name){
        this.name = name;
        this.isReady = false;
        this.score = 0;
        this.index = 0;
        this.gameID = 0;    //zero corresponds to the game lobby
        this.color = 'W';   //default to white color 
    }

}
