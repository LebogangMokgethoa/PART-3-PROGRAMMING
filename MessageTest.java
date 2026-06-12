package com.mycompany.userlogin;

import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

/**
 * Unit Tests for Message class — PROG5121 POE Part 2
 *
 * Test Case 1: recipient = +27718693002, message = "Hi Mike, can you join us for dinner tonight?"
 * Test Case 2: recipient = 08575975889,  message = "Hi Keegan, did you receive the payment?"
 */
public class MessageTest {

    private Message message1;
    private Message message2;

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        message1 = new Message("+27718693002",
                               "Hi Mike, can you join us for dinner tonight?");
        message2 = new Message("08575975889",
                               "Hi Keegan, did you receive the payment?");
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    // =========================================================
    //  MESSAGE LENGTH TESTS
    // =========================================================

    @Test
    public void testCheckMessageLength_Success() {
        assertEquals("Message ready to send.",
                     message1.checkMessageLength());
    }

    @Test
    public void testCheckMessageLength_Failure() {
        String longMessage = "A".repeat(260);
        Message longMsg = new Message("+27718693002", longMessage);

        String result = longMsg.checkMessageLength();

        // JUnit 4: message string comes FIRST
        assertTrue("Expected failure message but got: " + result,
                   result.contains("Message exceeds 250 characters by 10"));
    }

    // =========================================================
    //  RECIPIENT CELL NUMBER TESTS
    // =========================================================

    @Test
    public void testCheckRecipientCell_Success() {
        assertEquals("Cell phone number successfully captured.",
                     message1.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Failure() {
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain international code.",
            message2.checkRecipientCell()
        );
    }

    // =========================================================
    //  MESSAGE HASH TESTS
    // =========================================================

    @Test
    public void testMessageHash_IsGenerated() {
        String hash = message1.getMessageHash();
        assertNotNull("Hash should not be null", hash);
        assertTrue("Hash should start with '0x' but got: " + hash,
                   hash.startsWith("0x"));
    }

    @Test
    public void testMessageHash_AllMessages_InLoop() {
        Message[] messages = { message1, message2 };

        for (Message msg : messages) {
            String hash = msg.getMessageHash();
            assertNotNull("Hash should not be null for: " + msg.getMessageText(), hash);
            assertFalse("Hash should not be empty for: " + msg.getMessageText(), hash.isEmpty());
            assertTrue("Hash format incorrect for: " + msg.getMessageText(),
                       hash.startsWith("0x"));
        }
    }

    // =========================================================
    //  MESSAGE ID TEST
    // =========================================================

    @Test
    public void testMessageID_IsCreated() {
        String id = message1.getMessageID();
        assertNotNull("Message ID should not be null", id);
        assertTrue("Message ID should start with '#' but got: " + id,
                   id.startsWith("#"));
        assertEquals("Message ID should be 11 characters long", 11, id.length());
    }

    // =========================================================
    //  SENT MESSAGE TESTS
    // =========================================================

    @Test
    public void testSentMessage_Send() {
        assertEquals("Message successfully sent.",
                     message1.sentMessage(1));
    }

    @Test
    public void testSentMessage_Disregard() {
        assertEquals("Press 0 to delete the message.",
                     message2.sentMessage(2));
    }

    @Test
    public void testSentMessage_Store() {
        assertEquals("Message successfully stored.",
                     message1.sentMessage(3));
    }

    // =========================================================
    //  RETURN TOTAL MESSAGES SENT
    // =========================================================

    @Test
    public void testReturnTotalMessages() {
        message1.sentMessage(1);
        String result = Message.returnTotalMessages();

        assertTrue("Should return total count string but got: " + result,
                   result.startsWith("Total messages sent:"));
    }

    /**
     * Test of main method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Message.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkMessageLength method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testCheckMessageLength() {
        System.out.println("checkMessageLength");
        Message instance = null;
        String expResult = "";
        String result = instance.checkMessageLength();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkRecipientCell method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testCheckRecipientCell() {
        System.out.println("checkRecipientCell");
        Message instance = null;
        String expResult = "";
        String result = instance.checkRecipientCell();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sentMessage method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testSentMessage() {
        System.out.println("sentMessage");
        int action = 0;
        Message instance = null;
        String expResult = "";
        String result = instance.sentMessage(action);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printMessages method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testPrintMessages() {
        System.out.println("printMessages");
        Message instance = null;
        String expResult = "";
        String result = instance.printMessages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecipient method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetRecipient() {
        System.out.println("getRecipient");
        Message instance = null;
        String expResult = "";
        String result = instance.getRecipient();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageText method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetMessageText() {
        System.out.println("getMessageText");
        Message instance = null;
        String expResult = "";
        String result = instance.getMessageText();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageID method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetMessageID() {
        System.out.println("getMessageID");
        Message instance = null;
        String expResult = "";
        String result = instance.getMessageID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageHash method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetMessageHash() {
        System.out.println("getMessageHash");
        Message instance = null;
        String expResult = "";
        String result = instance.getMessageHash();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSentMessages method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetSentMessages() {
        System.out.println("getSentMessages");
        ArrayList<Message> expResult = null;
        ArrayList<Message> result = Message.getSentMessages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDisregardedMessages method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetDisregardedMessages() {
        System.out.println("getDisregardedMessages");
        ArrayList<Message> expResult = null;
        ArrayList<Message> result = Message.getDisregardedMessages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStoredMessages method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetStoredMessages() {
        System.out.println("getStoredMessages");
        ArrayList<Message> expResult = null;
        ArrayList<Message> result = Message.getStoredMessages();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageHashes method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetMessageHashes() {
        System.out.println("getMessageHashes");
        ArrayList<String> expResult = null;
        ArrayList<String> result = Message.getMessageHashes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageIDs method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetMessageIDs() {
        System.out.println("getMessageIDs");
        ArrayList<String> expResult = null;
        ArrayList<String> result = Message.getMessageIDs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMessageToArray method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testAddMessageToArray() {
        System.out.println("addMessageToArray");
        Message msg = null;
        String flag = "";
        Message.addMessageToArray(msg, flag);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeMessage method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testStoreMessage() {
        System.out.println("storeMessage");
        String messageID = "";
        String messageHash = "";
        String recipient = "";
        String message = "";
        Message.storeMessage(messageID, messageHash, recipient, message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadStoredMessagesFromJSON method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testLoadStoredMessagesFromJSON() {
        System.out.println("loadStoredMessagesFromJSON");
        Message.loadStoredMessagesFromJSON();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of quickChat method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testQuickChat() {
        System.out.println("quickChat");
        Message.quickChat();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMessages method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testSendMessages() {
        System.out.println("sendMessages");
        Scanner input = null;
        Message.sendMessages(input);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storedMessagesMenu method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testStoredMessagesMenu() {
        System.out.println("storedMessagesMenu");
        Scanner scanner = null;
        Message.storedMessagesMenu(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displaySenderRecipient method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testDisplaySenderRecipient() {
        System.out.println("displaySenderRecipient");
        Message.displaySenderRecipient();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLongestMessage method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetLongestMessage() {
        System.out.println("getLongestMessage");
        String expResult = "";
        String result = Message.getLongestMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayLongestMessage method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testDisplayLongestMessage() {
        System.out.println("displayLongestMessage");
        Message.displayLongestMessage();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchByMessageID method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testSearchByMessageID() {
        System.out.println("searchByMessageID");
        Scanner scanner = null;
        Message.searchByMessageID(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessageByID method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetMessageByID() {
        System.out.println("getMessageByID");
        String id = "";
        String expResult = "";
        String result = Message.getMessageByID(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchByRecipient method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testSearchByRecipient() {
        System.out.println("searchByRecipient");
        Scanner scanner = null;
        Message.searchByRecipient(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMessagesByRecipient method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testGetMessagesByRecipient() {
        System.out.println("getMessagesByRecipient");
        String recipient = "";
        ArrayList<String> expResult = null;
        ArrayList<String> result = Message.getMessagesByRecipient(recipient);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteByHash method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testDeleteByHash() {
        System.out.println("deleteByHash");
        Scanner scanner = null;
        Message.deleteByHash(scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteMessageByHash method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testDeleteMessageByHash() {
        System.out.println("deleteMessageByHash");
        String hash = "";
        String expResult = "";
        String result = Message.deleteMessageByHash(hash);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of displayReport method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testDisplayReport() {
        System.out.println("displayReport");
        Message.displayReport();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printAllSent method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testPrintAllSent() {
        System.out.println("printAllSent");
        Message.printAllSent();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printAllStored method, of class Message.
     */
    @org.junit.jupiter.api.Test
    public void testPrintAllStored() {
        System.out.println("printAllStored");
        Message.printAllStored();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}