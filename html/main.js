var connection = null;
let wordgrid = null;
var serverUrl;
let gridsize = 30;
inputCoords = [];
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
    console.log("Start:" + "[" + inputCoords[0] + "]" +
                " End:" + "[" +inputCoords[1] + "]");    inputCoords = [];
  }
}