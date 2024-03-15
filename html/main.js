var connection = null;
let wordgrid = null;
var serverUrl;
let gridsize = 30;
let name = null;
inputCoords = [];

// Class for UserMessage
class UserMsg{
  code = null;
  name = null;
  startCoords = [];
  endCoords = [];
  GameNum = null;
}



//logs the URL of the websocket server, which sits on port 9880
serverUrl = "ws://" + window.location.hostname + ":9880";
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
}

connection.onmessage = function(evt){
  var msg = evt.data;
  
  //TO DO: IMPLEMENT CHECKING THE KIND OF MESSAGE WE GOT TO AVOID
  // UPDATING THE GRID UNNECESSARILY

  //Try and parse a 2d JSON array out of this thing.
  if (typeof msg !== "string") {
    console.log("not a string");
  }
  try {
    let obj = JSON.parse(msg);
    wordgrid = obj;
    for (let i = 0; i < gridsize; i++) {
      for (let j = 0; j < gridsize; j++) {
        document.getElementById(i + "," + j).innerHTML = wordgrid[i][j];
      }
    }
  } 
  catch (error) {
      console.log("not a JSON");
      console.log("App.java WS: " + msg);
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
  console.log("Requested server for name: " + x);
  U = new UserMsg;
  U.code = 100;
  U.name = x;
  U.startCoords = [1,3];
  U.endCoords = [5,7];
  U.GameNum = 0;
  connection.send(JSON.stringify(U));
}
