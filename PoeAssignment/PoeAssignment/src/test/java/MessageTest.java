package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    Message msg1, msg2, msg3, msg4, msg5;

    @BeforeEach
    public void setup() {
        // Clear arrays before each test
        Message.sentMessages.clear();
        Message.storedMessages.clear();
        Message.disregardedMessages.clear();
        Message.messageHashes.clear();
        Message.messageIds.clear();

        // Populate with test data
        msg1 = new Message(1, "+27834557896", "Did you get the cake?");
        Message.addSentMessage(msg1);

        msg2 = new Message(2, "+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message.addStoredMessage(msg2);

        msg3 = new Message(3, "+27834484567", "Yohoooo, I am at your gate.");
        Message.addDisregardedMessage(msg3);

        msg4 = new Message(4, "0838884567", "It is dinner time!");
        Message.addSentMessage(msg4);

        msg5 = new Message(5, "+27838884567", "Ok, I am leaving without you.");
        Message.addStoredMessage(msg5);
    }

    // 1) Sent messages array correctly populated
    @Test
    public void testSentMessagesArray() {
        assertEquals(2, Message.sentMessages.size());
        assertEquals("Did you get the cake?", Message.sentMessages.get(0).getMessageText());
        assertEquals("It is dinner time!", Message.sentMessages.get(1).getMessageText());
    }

    // 2) Display the longest message
    @Test
    public void testLongestMessage() {
        // Longest among msg1–msg4 is msg2
        String longest = msg2.getMessageText();
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest);
    }

    // 3) Search for messageID (msg4)
    @Test
    public void testSearchByMessageId() {
        String id = msg4.getMessageId();
        // Simulate search
        Message found = Message.sentMessages.stream()
                .filter(m -> m.getMessageId().equals(id))
                .findFirst()
                .orElse(null);

        assertNotNull(found);
        assertEquals("It is dinner time!", found.getMessageText());
    }

    // 4) Search all messages sent/stored to recipient +27838884567
    @Test
    public void testSearchByRecipient() {
        String recipient = "+27838884567";
        long count = Message.storedMessages.stream()
                .filter(m -> m.getRecipient().equals(recipient))
                .count();

        assertEquals(2, count);
        assertEquals("Where are you? You are late! I have asked you to be on time.", Message.storedMessages.get(0).getMessageText());
        assertEquals("Ok, I am leaving without you.", Message.storedMessages.get(1).getMessageText());
    }

    // 5) Delete a message using message hash (msg2)
    @Test
    public void testDeleteByMessageHash() {
        String hashToDelete = msg2.getMessageHash();
        Message.deleteByMessageHash(hashToDelete);

        boolean stillExists = Message.sentMessages.stream()
                .anyMatch(m -> m.getMessageHash().equals(hashToDelete));

        assertFalse(stillExists);
    }

    // 6) Display report of sent messages
    @Test
    public void testDisplayReport() {
        assertEquals(2, Message.sentMessages.size());
        assertEquals("Did you get the cake?", Message.sentMessages.get(0).getMessageText());
        assertEquals("It is dinner time!", Message.sentMessages.get(1).getMessageText());

        // Ensure hashes and IDs exist
        assertNotNull(Message.sentMessages.get(0).getMessageHash());
        assertNotNull(Message.sentMessages.get(0).getMessageId());
    }
}
