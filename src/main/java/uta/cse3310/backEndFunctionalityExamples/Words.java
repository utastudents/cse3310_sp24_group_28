package uta.cse3310.backEndFunctionalityExamples;
public class Words {
    

    public String word;         //the word itself
    public int x_startPoint;    //coordinates
    public int y_startPoint;
    public int x_endPoint;
    public int y_endPoint;



    Words(String word, int x_startPoint,int y_startPoint,int x_endPoint,int y_endPoint){
        this.word = word;
        this.x_startPoint = x_startPoint;
        this.y_startPoint = y_startPoint;
        this.x_endPoint = x_endPoint;
        this.y_endPoint = y_endPoint;
    }
}
