package uta.cse3310;

import junit.framework.TestCase;
import org.json.simple.JSONObject;
import com.google.gson.JsonObject;

// classes to illustrate the concept
class UserMsg {
    int code;
  String name;
  String message;
  int startCoords[];
  int endCoords[];
  int gameNum;
}

class ChatHandler {
    public JSONObject handleChatMessage(UserMsg userMsg) {
        JSONObject chatMessage = new JSONObject();
        chatMessage.put("type", "chat");
        chatMessage.put("sender", userMsg.name);
        chatMessage.put("content", userMsg.message);
        return chatMessage; // This message will be sent somewhere, not returned
    }
}

public class ChatTest extends TestCase {

    public void testHandleChatMessage() {

        ChatHandler chatHandler = new ChatHandler();
        UserMsg userMsg = new UserMsg();
        userMsg.name = "Sam";
        userMsg.message = "Welcome to TWSG!";

        JSONObject result = chatHandler.handleChatMessage(userMsg);

        assertEquals("chat", result.get("type"));
        assertEquals("Sam", result.get("sender"));
        assertEquals("Welcome to TWSG!", result.get("content"));
    }
}
