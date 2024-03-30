var connection = null;
let wordgrid = null;
var serverUrl;
let gridsize = 30;
inputCoords = [];

// Player attributes?
let name = null;

// Class for UserMessage
class UserMsg{
  code = null;
  name = null;
  startCoords = [];
  endCoords = [];
}


//logs the URL of the websocket server, which sits on port 9880
serverUrl = "ws://" + window.location.hostname + ":9128";
connection = new WebSocket(serverUrl);


connection.onopen = function (evt) {
  console.log("opened a websocket to the App.java Websocket Server");
  var grid = document.getElementById("grid");
  for (let i = 0; i < gridsize; i++) {
    var row = document.createElement("tr");
    for (let j = 0; j < gridsize; j++) {
      let cell = document.createElement("td");
      cell.className = "clickable";
      cell.id = i + "," + j;
      cell.setAttribute("onclick",`scream(${i},${j})`);
      cell.addEventListener("click", function () {
        this.classList.toggle("activated");
      });
      row.appendChild(cell);
    }
    grid.appendChild(row);
  }
  grid.style.display = "none";
}

connection.onmessage = function(evt){
  // rework this
  // if it can be parsed into a json, then it's data
  // for updating the interface
  // if it aint, then it's just a reply code for the front end to read

  var msg = evt.data;

  //TO DO: IMPLEMENT CHECKING THE KIND OF MESSAGE WE GOT TO AVOID
  // UPDATING THE GRID UNNECESSARILY

  //Try and parse a 2d JSON array out of this thing.
  try {
    let obj = JSON.parse(msg);

    // if the object is a Lobby
    console.log(msg);
    if("playerNames" in obj){
      let lobbyTitle = "Lobby: " + obj.gamesAvailable;
      document.getElementById("lobbyTitle").innerHTML = lobbyTitle;
      let lobbyTable = document.getElementById("lobbyList");
      lobbyTable.innerHTML = "";
      if(obj.numReady >= 2 && obj.gamesAvailable > 0){
        document.getElementById("gameReadyMsg").style.display = "block";
        document.getElementById("startGameButton").disabled = false;
      }
      else{
        document.getElementById("gameReadyMsg").style.display = "none";
        document.getElementById("startGameButton").disabled = true;
      }
      for(let i = 0; i < obj.numPlayers; i++){
        // console.log(obj.playerNames[i]);
        let row = document.createElement("tr");

        let nameCell = document.createElement("td");
        nameCell.innerHTML = obj.playerNames[i];
        let statusCell = document.createElement("td");
        if(obj.playerStatuses[i] == false){
          statusCell.innerHTML = "Not Ready";
          statusCell.style.color = "red";
        }
        else{
          statusCell.innerHTML = "Ready";
          statusCell.style.color = "green";
        }
        nameCell.setAttribute("id","nameCell");
        statusCell.setAttribute("id","statusCell");
        
        row.appendChild(nameCell);
        row.appendChild(statusCell);
        lobbyTable.appendChild(row);
      }
    }
    // if we're reading a Game.java object
    else if("isOpen" in obj){
      document.getElementById("lobby").style.display = "none";
      document.getElementById("gameArea").style.display = "block";
      document.getElementById("grid").style.display = "block";
      wordgrid = obj.matrix;
      for (let i = 0; i < gridsize; i++) {
        for (let j = 0; j < gridsize; j++) {
          document.getElementById(i + "," + j).innerHTML = wordgrid[i][j];
        }
      }
      console.log(obj.scoreList);
    }
    else{
      console.log("can't");
    }
  } 
  catch (error) {
    console.log("WS Server: " + msg);
    if(msg == "approved"){
      document.title = document.getElementById("name").value;
      document.getElementById("serverMessage").style.display = "none";
      document.getElementById("name").value = "";
      document.getElementById("textInput").style.display = "none";
      document.getElementById("lobby").style.display = "block";
    }
    else if(msg == "unapproved"){
      document.getElementById("serverMessage").innerHTML = "Name already taken";
    }
  }
  
  
}

//TO DO: set up functionality for validating the coords -> check slopes and stuff
  //     then send it to server for processing.
function scream(i,j){
  console.log(`called [${i}][${j}]`);
  inputCoords.push([i,j]);
  if(inputCoords.length == 2){
    console.log("Start:" + "[" + inputCoords[0] + "]" + " End:" + "[" +inputCoords[1] + "]");
    //parse input
    // [i][0] = serves as y coord, [i][1] serves as x coordinate
    dy = inputCoords[1][1] - inputCoords[0][1] 
    dx = inputCoords[1][0] - inputCoords[0][0]
    console.log(dy + "/" + dx);
    // if dy or dx is zero, then it's vertical or horizontal
    // if(inputCoords)
    inputCoords = [];
  }
}

function submitName(){
  let x = document.getElementById("name").value;
  this.name = x;
  console.log("Requested server for name: " + this.name);
  U = new UserMsg;
  U.name = this.name;
  U.code = 100;
  connection.send(JSON.stringify(U));
}

function toggleReady(){
  console.log("i am " + this.name);
  console.log("Ping the server. Toggle my Ready Status");
  U = new UserMsg;
  U.name = this.name;
  U.code = 200;
  connection.send(JSON.stringify(U))
}

function startGame(){
  console.log("Requested the server to start a game.");
  U = new UserMsg;
  U.name = this.name;
  U.code = 300;
  connection.send(JSON.stringify(U));
}

function ping(){
  U = new UserMsg;
  U.name = this.name;
  U.code = 400;
  connection.send(JSON.stringify(U));
}