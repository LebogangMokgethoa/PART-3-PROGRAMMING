/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.userlogin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Lebogang Mokgethoa
 * Part 3
 */
public class MessageJUnitTest {

    private Message msg1, msg2, msg3, msg4, msg5;

    @BeforeEach
    public void setUp() {
        // Clear all static collections before each test
        Message.getSentMessages().clear();
        Message.getStoredMessages().clear();
        Message.getDisregardedMessages().clear();
        Message.getMessageHashes().clear();
        Message.getMessageIDs().clear();

        msg1 = new Message("+27834557896", "Did you get the cake?");
        Message.addMessageToArray(msg1, "sent");

        msg2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.");
        Message.addMessageToArray(msg2, "stored");

        msg3 = new Message("+27834484567", "Yohoooo, I am at your gate.");
        Message.addMessageToArray(msg3, "disregard");

        msg4 = new Message("0838884567", "It is dinner time!");
        Message.addMessageToArray(msg4, "sent");

        msg5 = new Message("+27838884567", "Ok, I am leaving without you.");
        Message.addMessageToArray(msg5, "stored");
    }

    @Test
    public void testSentMessagesArrayPopulated() {
        ArrayList<Message> sent = Message.getSentMessages();
        assertEquals(2, sent.size());
        assertEquals("Did you get the cake?", sent.get(0).getMessageText());
        assertEquals("It is dinner time!", sent.get(1).getMessageText());
    }

    @Test
    public void testStoredMessagesArrayPopulated() {
        ArrayList<Message> stored = Message.getStoredMessages();
        assertEquals(2, stored.size());
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                     stored.get(0).getMessageText());
        assertEquals("Ok, I am leaving without you.",
                     stored.get(1).getMessageText());
    }

    @Test
    public void testDisregardedMessagesArrayPopulated() {
        ArrayList<Message> disregarded = Message.getDisregardedMessages();
        assertEquals(1, disregarded.size());
        assertEquals("Yohoooo, I am at your gate.",
                     disregarded.get(0).getMessageText());
    }

    @Test
    public void testLongestMessage() {
        String longest = Message.getLongestMessage();
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                     longest);
    }

    @Test
    public void testSearchByMessageID() {
        String result = Message.getMessageByID(msg4.getMessageID());
        assertTrue(result.contains("It is dinner time!"));
        assertTrue(result.contains(msg4.getRecipient()));
    }

    @Test
    public void testSearchByInvalidMessageID() {
        String result = Message.getMessageByID("INVALID_ID_12345");
        assertEquals("Message ID not found.", result);
    }

    @Test
    public void testMessageHashing() {
        String hash1 = msg1.getMessageHash();
        String hash2 = msg2.getMessageHash();
        String hash3 = msg3.getMessageHash();

        assertNotNull(hash1);
        assertNotNull(hash2);
        assertNotNull(hash3);
        assertNotEquals(hash1, hash2);
        assertNotEquals(hash1, hash3);
    }

    @Test
    public void testDuplicatePhoneNumber() {
        ArrayList<Message> stored = Message.getStoredMessages();
        assertEquals("+27838884567", stored.get(0).getRecipient());
        assertEquals("+27838884567", stored.get(1).getRecipient());
        assertEquals(stored.get(0).getRecipient(), stored.get(1).getRecipient());
    }

    @Test
    public void testAllMessagesHaveUniqueID() {
        String id1 = msg1.getMessageID();
        String id2 = msg2.getMessageID();
        String id3 = msg3.getMessageID();
        String id4 = msg4.getMessageID();
        String id5 = msg5.getMessageID();

        assertNotEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertNotEquals(id1, id4);
        assertNotEquals(id1, id5);
        assertNotEquals(id2, id3);
        assertNotEquals(id2, id4);
        assertNotEquals(id2, id5);
        assertNotEquals(id3, id4);
        assertNotEquals(id3, id5);
        assertNotEquals(id4, id5);
    }

    @Test
    public void testGetMessageByIDReturnsCorrectMessage() {
        String result1 = Message.getMessageByID(msg1.getMessageID());
        String result2 = Message.getMessageByID(msg2.getMessageID());
        String result3 = Message.getMessageByID(msg3.getMessageID());

        assertTrue(result1.contains("Did you get the cake?"));
        assertTrue(result2.contains("Where are you? You are late!"));
        assertTrue(result3.contains("Yohoooo, I am at your gate."));
    }
}