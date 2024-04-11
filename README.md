Change directory to your project directory

cd cse3310_sp24_group_28

Clean the project

mvn clean

Compile the project

mvn compile

Set environment variables for HTTP and WebSocket ports

export HTTP_PORT=9028export WEBSOCKET_PORT=9128

Run the main class using Maven

mvn exec:java -Dexec.mainClass=uta.cse3310.Main
