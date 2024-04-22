package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ChatTest extends TestCase {

    public ChatTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(ChatTest.class);
    }

    public void sendMessage() {
        Chat chat = new Chat();
        String message = "Hello, world!";
        String expected = "You said: " + message;
        String actual = chat.sendMessage(message);
        assertEquals(expected, actual);
    }

    public void receiveMessage() {
        Chat chat = new Chat();
        String message = "Hello, world!";
        String expected = "Received: " + message;
        String actual = chat.receiveMessage(message);
        assertEquals(expected, actual);
    }
}
