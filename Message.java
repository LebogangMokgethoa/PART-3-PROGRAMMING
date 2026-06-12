package com.mycompany.userlogin;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Lebogang Mokgethoa
 * @version 3.0
 * Handles messaging: sending, storing, displaying, and validation.
 * Part 3: Arrays, JSON loading, and Stored Messages Menu all built in.
 */
public class Message {

    // =============================================
    // INSTANCE FIELDS
    // =============================================
    private String recipient;
    private String messageText;
    private String messageID;
    private String messageHash;

    private static int totalMessagesSent = 0;

    // =============================================
    // PART 3 — THE FIVE REQUIRED ARRAYS (static so
    // they are shared across the whole session)
    // =============================================
    private static ArrayList<Message> sentMessages        = new ArrayList<>();
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static ArrayList<Message> storedMessages      = new ArrayList<>();
    private static ArrayList<String>  messageHashes       = new ArrayList<>();
    private static ArrayList<String>  messageIDs          = new ArrayList<>();

    // =============================================
    // CONSTRUCTOR
    // =============================================
    public Message(String recipient, String messageText) {
        this.recipient   = recipient;
        this.messageText = messageText;
        this.messageID   = generateMessageID();
        this.messageHash = generateMessageHash();
    }

    // =============================================
    // MAIN
    // =============================================
    public static void main(String[] args) {
        quickChat();
    }

    // =============================================
    // ID AND HASH GENERATION
    // =============================================
    private String generateMessageID() {
        Random rand = new Random();
        long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return "#" + id;
    }

    private String generateMessageHash() {
        int hash = 0;
        for (char c : messageText.toCharArray()) {
            hash = (hash * 31 + c) & 0xFFFFFF;
        }
        return "0x" + String.format("%06X", hash);
    }

    // =============================================
    // CORE VALIDATION METHODS
    // =============================================
    public String checkMessageLength() {
        if (messageText.length() > 250) {
            return "Message exceeds 250 characters by "
                    + (messageText.length() - 250)
                    + " characters. Please reduce your message.";
        }
        return "Message ready to send.";
    }

    public String checkRecipientCell() {
        if (recipient.startsWith("+27")
                && recipient.length() == 12
                && recipient.substring(3).matches("\\d{9}")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain international code.";
    }

    public String sentMessage(int action) {
        switch (action) {
            case 1:
                totalMessagesSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public String printMessages() {
        return "Message ID:   " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient:    " + recipient
                + "\nMessage:      " + messageText;
    }

    public static String returnTotalMessages() {
        return "Total messages sent: " + totalMessagesSent;
    }

    // =============================================
    // GETTERS
    // =============================================
    public String getRecipient()    { return recipient; }
    public String getMessageText()  { return messageText; }
    public String getMessageID()    { return messageID; }
    public String getMessageHash()  { return messageHash; }

    // =============================================
    // PART 3 — ARRAY GETTERS (used by JUnit tests)
    // =============================================
    public static ArrayList<Message> getSentMessages()        { return sentMessages; }
    public static ArrayList<Message> getDisregardedMessages() { return disregardedMessages; }
    public static ArrayList<Message> getStoredMessages()      { return storedMessages; }
    public static ArrayList<String>  getMessageHashes()       { return messageHashes; }
    public static ArrayList<String>  getMessageIDs()          { return messageIDs; }

    // =============================================
    // PART 3 — ROUTE MESSAGE INTO CORRECT ARRAY
    // =============================================
    public static void addMessageToArray(Message msg, String flag) {
        switch (flag.toLowerCase()) {
            case "sent":
                sentMessages.add(msg);
                break;
            case "stored":
                storedMessages.add(msg);
                break;
            case "disregard":
                disregardedMessages.add(msg);
                break;
        }
        // Always track hash and ID
        messageHashes.add(msg.getMessageHash());
        messageIDs.add(msg.getMessageID());
    }

    // =============================================
    // STORE MESSAGE TO JSON FILE (Part 2 — unchanged)
    // =============================================
    public static void storeMessage(String messageID, String messageHash,
                                    String recipient, String message) {
        try {
            FileWriter writer = new FileWriter("storedMessages.json", true);
            writer.write("{\n");
            writer.write("  \"MessageID\":   \"" + messageID   + "\",\n");
            writer.write("  \"MessageHash\": \"" + messageHash + "\",\n");
            writer.write("  \"Recipient\":   \"" + recipient   + "\",\n");
            writer.write("  \"Message\":     \"" + message     + "\"\n");
            writer.write("}\n\n");
            writer.close();
            System.out.println("Message saved to storedMessages.json");
        } catch (IOException error) {
            System.out.println("Error storing the message: " + error.getMessage());
        }
    }

    // =============================================
    // PART 3 — LOAD STORED MESSAGES FROM JSON FILE
    // Reads storedMessages.json line by line and
    // rebuilds Message objects into storedMessages array
    // =============================================
    public static void loadStoredMessagesFromJSON() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("storedMessages.json")));

            // Split into individual JSON blocks by "}"
            String[] blocks = content.split("\\}");

            for (String block : blocks) {
                block = block.trim();
                if (block.isEmpty()) continue;

                // Extract Recipient value
                String recipient = extractJSONValue(block, "Recipient");
                // Extract Message value
                String message   = extractJSONValue(block, "Message");

                if (!recipient.isEmpty() && !message.isEmpty()) {
                    Message msg = new Message(recipient, message);
                    storedMessages.add(msg);
                    messageHashes.add(msg.getMessageHash());
                    messageIDs.add(msg.getMessageID());
                }
            }

            System.out.println("Loaded " + storedMessages.size()
                    + " stored message(s) from storedMessages.json");

        } catch (IOException e) {
            System.out.println("No stored messages file found as yet. Starting fresh.");
        }
    }

    /** Helper: extracts a value from a JSON string by key */
    private static String extractJSONValue(String block, String key) {
        String search = "\"" + key + "\":";
        int start = block.indexOf(search);
        if (start == -1) return "";
        start = block.indexOf("\"", start + search.length()) + 1;
        int end = block.indexOf("\"", start);
        if (start <= 0 || end <= 0) return "";
        return block.substring(start, end);
    }

    // =============================================
    // QUICKCHAT MENU — UPDATED FOR PART 3
    // =============================================
    public static void quickChat() {
        Scanner input = new Scanner(System.in);

        // Load any previously stored messages from JSON
        loadStoredMessagesFromJSON();

        boolean running = true;
        System.out.println("\nKwigchaes-e osin geos-eul hwan-yeonghabnida (Welcome to QuickChat) :) :) !!");

        while (running) {
            System.out.println("\n==========================");
            System.out.println("       QUICKCHAT MENU");
            System.out.println("==========================");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Sent Messages");
            System.out.println("3. Show Stored Messages");
            System.out.println("4. Stored Messages Menu");
            System.out.println("5. Quit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    sendMessages(input);
                    break;
                case 2:
                    printAllSent();
                    break;
                case 3:
                    printAllStored();
                    break;
                case 4:
                    storedMessagesMenu(input);
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-5.");
            }
        }

        System.out.println("\n" + Message.returnTotalMessages());
        input.close();
    }

    // =============================================
    // SEND MESSAGES — UPDATED FOR PART 3
    // =============================================
    static void sendMessages(Scanner input) {

        System.out.print("How many messages would you like to send? ");

        int numMessages;
        try {
            numMessages = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            input.nextLine();
            System.out.println("Invalid number. Returning to menu.");
            return;
        }

        for (int i = 0; i < numMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + " of " + numMessages + " ---");

            // Validate recipient
            String recipient;
            String recipientCheck;
            do {
                System.out.print("Enter recipient number (+27...): ");
                recipient = input.nextLine();
                Message tempMsg = new Message(recipient, "");
                recipientCheck = tempMsg.checkRecipientCell();
                System.out.println(recipientCheck);
            } while (!recipientCheck.equals("Cell phone number successfully captured."));

            // Validate message length
            String messageText;
            String lengthCheck;
            do {
                System.out.print("Enter message (max 250 chars): ");
                messageText = input.nextLine();
                Message tempMsg = new Message(recipient, messageText);
                lengthCheck = tempMsg.checkMessageLength();
                System.out.println(lengthCheck);
            } while (!lengthCheck.equals("Message ready to send."));

            // Create final validated message object
            Message msg = new Message(recipient, messageText);

            System.out.println("Message ID:   " + msg.getMessageID());
            System.out.println("Message Hash: " + msg.getMessageHash());

            System.out.println("\n1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message");
            System.out.print("Choice: ");

            int action;
            try {
                action = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                input.nextLine();
                System.out.println("Invalid input. Message disregarded.");
                action = 2;
            }

            String result = msg.sentMessage(action);
            System.out.println(result);

            // Route into correct array
            if (action == 1) {
                addMessageToArray(msg, "sent");
                System.out.println("\n" + msg.printMessages());
            } else if (action == 2) {
                addMessageToArray(msg, "disregard");
            } else if (action == 3) {
                storeMessage(msg.getMessageID(), msg.getMessageHash(),
                             msg.getRecipient(), msg.getMessageText());
                addMessageToArray(msg, "stored");
            }
        }
    }

    // =============================================
    // PART 3 — STORED MESSAGES MENU (Option 4)
    // =============================================
    public static void storedMessagesMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\n============================");
            System.out.println("   STORED MESSAGES MENU :) :) ");
            System.out.println("==============================");
            System.out.println("a. Display recipient of all stored messages");
            System.out.println("b. Display the longest message");
            System.out.println("c. Search by Message ID");
            System.out.println("d. Search messages by recipient");
            System.out.println("e. Delete a message using message hash");
            System.out.println("f. Display full report of all sent messages");
            System.out.println("q. Back to main menu");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a": displaySenderRecipient();      break;
                case "b": displayLongestMessage();       break;
                case "c": searchByMessageID(scanner);   break;
                case "d": searchByRecipient(scanner);   break;
                case "e": deleteByHash(scanner);         break;
                case "f": displayReport();               break;
                case "q": back = true;                   break;
                default:  System.out.println("Invalid option. Try again."); break;
            }
        }
    }

    // ---- a ---------------------------------------
    public static void displaySenderRecipient() {
        System.out.println("\n----Stored Messages: Recipients ---");
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        for (Message msg : storedMessages) {
            System.out.println("Recipient : " + msg.getRecipient());
            System.out.println("Message   : " + msg.getMessageText());
            System.out.println("--------------------");
        }
    }

    // --- b --------------------------------------------------
    public static String getLongestMessage() {
        ArrayList<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);

        if (all.isEmpty()) return "No messages available.";

        Message longest = all.get(0);
        for (Message msg : all) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        return longest.getMessageText();
    }

    public static void displayLongestMessage() {
        System.out.println("\n--- Longest Message ----");
        System.out.println(getLongestMessage());
    }

    // --- c --------------------------------------------
    public static void searchByMessageID(Scanner scanner) {
        System.out.print("\nEnter Message ID to search: ");
        String searchID = scanner.nextLine().trim();
        System.out.println(getMessageByID(searchID));
    }

    public static String getMessageByID(String id) {
        ArrayList<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);
        all.addAll(disregardedMessages);

        for (Message msg : all) {
            if (msg.getMessageID().equalsIgnoreCase(id)) {
                return "Recipient : " + msg.getRecipient()
                     + "\nMessage   : " + msg.getMessageText();
            }
        }
        return "Message ID not found.";
    }

    // ---- d -------------------------------------------
    public static void searchByRecipient(Scanner scanner) {
        System.out.print("\nEnter recipient number to search: ");
        String recipient = scanner.nextLine().trim();

        ArrayList<String> results = getMessagesByRecipient(recipient);
        if (results.isEmpty()) {
            System.out.println("No messages found for: " + recipient);
        } else {
            System.out.println("\n---Messages for " + recipient + " ----");
            for (String m : results) System.out.println(m);
        }
    }

    public static ArrayList<String> getMessagesByRecipient(String recipient) {
        ArrayList<String> results = new ArrayList<>();
        ArrayList<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);

        for (Message msg : all) {
            if (msg.getRecipient().equalsIgnoreCase(recipient)) {
                results.add(msg.getMessageText());
            }
        }
        return results;
    }

    // --- e --------------------------------------------
    public static void deleteByHash(Scanner scanner) {
        System.out.print("\nEnter message hash to delete: ");
        String hash = scanner.nextLine().trim();
        System.out.println(deleteMessageByHash(hash));
    }

    public static String deleteMessageByHash(String hash) {
        for (int i = 0; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).getMessageHash().equalsIgnoreCase(hash)) {
                String text = storedMessages.get(i).getMessageText();
                storedMessages.remove(i);
                messageHashes.remove(hash);
                return "Message: \"" + text + "\" successfully deleted.";
            }
        }
        return "Hash not found. No message deleted.";
    }

    // ---- f ------------------------------------------
    public static void displayReport() {
        System.out.println("\n=================================");
        System.out.println("         SENT MESSAGES REPORT     ");
        System.out.println("======================================");

        if (sentMessages.isEmpty()) {
            System.out.println("No sent messages to display.");
            return;
        }

        for (Message msg : sentMessages) {
            System.out.println("Hash      : " + msg.getMessageHash());
            System.out.println("Recipient : " + msg.getRecipient());
            System.out.println("Message   : " + msg.getMessageText());
            System.out.println("==============================");
        }
    }

    // =============================================
    // DISPLAY METHODS
    // =============================================
    static void printAllSent() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages sent yet.");
        } else {
            System.out.println("\n--- Sent Messages ---");
            for (Message msg : sentMessages) {
                System.out.println("\n" + msg.printMessages());
            }
        }
    }

    static void printAllStored() {
        if (storedMessages.isEmpty()) {
            System.out.println("No messages stored yet.");
        } else {
            System.out.println("\n--- Stored Messages ---");
            for (Message msg : storedMessages) {
                System.out.println("\n" + msg.printMessages());
            }
        }
    }
}