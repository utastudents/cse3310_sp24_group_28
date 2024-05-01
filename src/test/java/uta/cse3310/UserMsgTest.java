package uta.cse3310;
import junit.framework.TestCase;

public class UserMsgTest extends TestCase {

    public void testUserMsgInitialization() {
        int[] startCoords = {1, 2};
        int[] endCoords = {3, 4};

        UserMsg userMsg = new UserMsg();
        userMsg.code = 100;
        userMsg.name = "Alice";
        userMsg.message = "Hello!";
        userMsg.startCoords = startCoords;
        userMsg.endCoords = endCoords;
        userMsg.gameNum = 1;

        assertEquals(100, userMsg.code);
        assertEquals("Alice", userMsg.name);
        assertEquals("Hello!", userMsg.message);
        assertEquals(1, userMsg.gameNum);
    }
}
