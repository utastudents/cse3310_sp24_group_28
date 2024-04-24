package uta.cse3310;

import java.util.ArrayList;

public class Lobby {
  public ArrayList<String> playerNames = new ArrayList<String>();
  public ArrayList<Boolean> playerStatuses = new ArrayList<Boolean>();
  public int numPlayers = 0;
  public int numReady = 0;
  public int gamesAvailable = 0;

  
  Lobby(ArrayList<Player> playerList){
    for(Player x: playerList){
      playerNames.add(x.name);
      playerStatuses.add(x.isReady);
      numPlayers++;
    }
  }
  public void updateLobby(ArrayList<Player> playerList){
    this.playerNames.clear();
    this.playerStatuses.clear();
    this.numPlayers = 0;
    this.numReady = 0;
    for(Player x: playerList){
      playerNames.add(x.name);
      playerStatuses.add(x.isReady);
      if(x.isReady == true){
        numReady++;
      }
      numPlayers++;
    }

    
    
  }

  public void updateGamesAvailable(Game[] games){
    gamesAvailable = 0;
    for(Game g: games){
      if(g.isOpen == true){
        gamesAvailable++;
      }
    }
    
  }

  public void startGame(Player player){
    //after a player as joined a game, their status be ingame
    player.ingame = true;
    player.isReady = false;
  }
  public void displayStatus(Player player){
    if(player.isReady == false && player.ingame == false){
      System.out.println("Status: not ready");
    }
    else if(player.isReady == true && player.ingame == false){
      System.out.println("Status: ready");
    }
    else if(player.isReady == false && player.ingame == true){
      System.out.println("Status: ingame");
    }
  }
  public void displayPlayerName(Player player){
    System.out.println("Player: " + player.name);
  }

}
