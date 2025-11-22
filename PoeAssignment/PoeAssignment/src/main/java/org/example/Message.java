package org.example;

import java.util.ArrayList;
import java.util.Random;

public class Message {
    private String messageId;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // Collections
    public static ArrayList<Message> sentMessages = new ArrayList<>();
    public static ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static ArrayList<Message> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIds = new ArrayList<>();

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageId = generateMessageId();
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();

        messageIds.add(this.messageId);
        messageHashes.add(this.messageHash);
    }

    private String generateMessageId() {
        Random rand = new Random();
        long id = 1000000000L + (long) (rand.nextDouble() * 8999999999L);
        return String.valueOf(id);
    }

    public String createMessageHash() {
        String[] words = (messageText == null) ? new String[0] : messageText.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        String prefix = (messageId != null && messageId.length() >= 2) ? messageId.substring(0, 2) : "00";
        return prefix + ":" + messageNumber + ":" + (first + last).toUpperCase();
    }

    // Validation methods required by MainGUI
    public boolean isValidRecipient() {
        if (recipient == null) return false;
        // Require leading '+' (international code) and at most 10 digits total (digits only)
        String digits = recipient.replaceAll("\\D", "");
        return recipient.startsWith("+") && digits.length() <= 10 && digits.length() > 0;
    }

    public boolean isValidMessageLength() {
        return messageText != null && messageText.length() <= 250;
    }

    public boolean isShortEnoughForTask() {
        return messageText != null && messageText.length() < 50;
    }

    // Arrays population
    public static void addSentMessage(Message msg) {
        sentMessages.add(msg);
    }

    public static void addDisregardedMessage(Message msg) {
        disregardedMessages.add(msg);
    }

    public static void addStoredMessage(Message msg) {
        storedMessages.add(msg);
    }

    // Reports
    public static void displaySendersAndRecipients() {
        for (Message msg : sentMessages) {
            System.out.println("Sender: SystemUser | Recipient: " + msg.getRecipient());
        }
    }

    public static void displayLongestSentMessage() {
        Message longest = null;
        for (Message msg : sentMessages) {
            if (longest == null || msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        if (longest != null) {
            System.out.println("Longest Sent Message: " + longest.getMessageText());
        } else {
            System.out.println("No messages sent yet.");
        }
    }

    public static void searchByMessageId(String id) {
        for (Message msg : sentMessages) {
            if (msg.getMessageId().equals(id)) {
                System.out.println("Message ID: " + id);
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getMessageText());
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    public static void searchByRecipient(String recipient) {
        boolean found = false;
        for (Message msg : sentMessages) {
            if (msg.getRecipient().equals(recipient)) {
                System.out.println("Message to " + recipient + ": " + msg.getMessageText());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found for recipient: " + recipient);
        }
    }

    public static void deleteByMessageHash(String hash) {
        Message toRemove = null;
        for (Message msg : sentMessages) {
            if (msg.getMessageHash().equals(hash)) {
                toRemove = msg;
                break;
            }
        }
        if (toRemove != null) {
            sentMessages.remove(toRemove);
            System.out.println("Message with hash " + hash + " deleted.");
        } else {
            System.out.println("Message hash not found.");
        }
    }

    public static void displaySentMessagesReport() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages have been sent.");
            return;
        }
        System.out.println("=== Sent Messages Report ===");
        for (Message msg : sentMessages) {
            System.out.println("Message ID: " + msg.getMessageId());
            System.out.println("Message Hash: " + msg.getMessageHash());
            System.out.println("Recipient: " + msg.getRecipient());
            System.out.println("Message: " + msg.getMessageText());
            System.out.println("---------------------------");
        }
    }

    // Getters
    public String getMessageId() { return messageId; }
    public int getMessageNumber() { return messageNumber; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getMessageHash() { return messageHash; }
}
