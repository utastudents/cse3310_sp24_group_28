package com.example;

public class UserMsg {
  int code;
  String name;
  int startCoords[];
  int endCoords[];
  int gameNum;
}




// codes and meaning

// 100 - Name request: User asking the server for a nickname. if the nickname is valid, textbox disappears and user is created. 
//        Lobby and Ready button are made visible.
//       User is added to Lobby. Lobby lists players that joined first at the top. Everytime a new player is made, 
  //     Lobby object should be updated for everyone, regardless of visibility. 

// 200 - Ready up: User pings the server to set their status to ready and update the lobby
// 
// 
// 
// 
// 
// 
// 
// 
// 
