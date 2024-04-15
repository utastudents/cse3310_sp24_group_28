package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private String name; 
    private String message; 

    private List<Message> chatMessages;

    public Chat() {
        this.chatMessages = new ArrayList<>();
    }

    public void addMessage(String name, String message) {
        Message newMessage = new Message(name, message);
        chatMessages.add(newMessage);
    }

    public void displayMessages() {
        for (Message msg : chatMessages) {
            System.out.println(msg.getName() + ": " + msg.getMessage());
        }
    }

    private class Message {
        private String name;
        private String message;

        public Message(String name, String message) {
            this.name = name;
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }
    }
}
