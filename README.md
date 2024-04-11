cd cse3310_sp24_group_28
mvn clean
mvn compile
export HTTP_PORT=9028
export WEBSOCKET_PORT=9128
mvn exec:java -Dexec.mainClass=uta.cse3310.Main
