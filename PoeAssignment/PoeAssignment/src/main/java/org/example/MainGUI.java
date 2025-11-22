package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainGUI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // === Registration Phase ===
        System.out.println("=== User Registration ===");
        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter last name: ");
        String lastName = input.nextLine();

        System.out.print("Enter South African phone number (with +27 code): ");
        String phoneNumber = input.nextLine();

        // Create Login object
        Login newUser = new Login(username, password, firstName, lastName, phoneNumber);

        // Attempt to register
        String registrationMessage = newUser.registerUser();
        System.out.println(registrationMessage);

        // Stop if registration failed
        if (!registrationMessage.equals("User registered successfully!")) {
            System.out.println("Registration failed. Exiting...");
            input.close();
            return;
        }

        // === Login Phase ===
        System.out.println("\n=== User Login ===");
        System.out.print("Enter username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter password: ");
        String loginPassword = input.nextLine();

        // Authenticate user
        boolean loginStatus = newUser.loginUser(loginUsername, loginPassword);
        System.out.println(newUser.returnLoginStatus(loginStatus));

        // === QuickChat Phase ===
        if (loginStatus) {
            System.out.print("\nHow many messages would you like to send? ");
            int messageLimit = Integer.parseInt(input.nextLine());
            int messagesSent = 0;
            ArrayList<Message> storedMessages = new ArrayList<>();

            while (true) {
                System.out.println("\n=== QuickChat Menu ===");
                System.out.println("1) Send Message");
                System.out.println("2) Show Recently Sent Messages");
                System.out.println("3) Quit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(input.nextLine());

                if (choice == 1) {
                    if (messagesSent < messageLimit) {
                        System.out.println("Welcome to QuickChat.");
                        System.out.print("Enter recipient cell number (with international code): ");
                        String recipient = input.nextLine();

                        System.out.print("Enter your message: ");
                        String messageText = input.nextLine();

                        Message msg = new Message(messagesSent + 1, recipient, messageText);

                        if (!msg.isValidRecipient()) {
                            System.out.println("Invalid recipient number. Must include international code and be no more than 10 digits.");
                            continue;
                        }

                        if (!msg.isValidMessageLength()) {
                            System.out.println("Message too long. Must be 250 characters or fewer.");
                            continue;
                        }

                        if (!msg.isShortEnoughForTask()) {
                            System.out.println("Please enter a message of less than 50 characters.");
                        } else {
                            System.out.println("Message sent.");
                        }

                        System.out.println("Message Hash: " + msg.getMessageHash());

                        System.out.println("\nChoose an action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        int action = Integer.parseInt(input.nextLine());

                        if (action == 1 || action == 3) {
                            messagesSent++;
                            if (action == 3) {
                                storedMessages.add(msg);
                            }

                            // Show message details using JOptionPane
                            String messageDetails = "Message ID: " + msg.getMessageId() + "\n"
                                    + "Message Hash: " + msg.getMessageHash() + "\n"
                                    + "Recipient: " + msg.getRecipient() + "\n"
                                    + "Message: " + msg.getMessageText();

                            JOptionPane.showMessageDialog(null, messageDetails, "Message Details", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            System.out.println("Message disregarded.");
                        }


                    } else {
                        System.out.println("Message limit reached. You cannot send more messages.");
                    }

                } else if (choice == 2) {
                    System.out.println("Coming Soon.");
                } else if (choice == 3) {
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }

            // Save stored messages to JSON
            if (!storedMessages.isEmpty()) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                try (FileWriter writer = new FileWriter("stored_messages.json")) {
                    gson.toJson(storedMessages, writer);
                    System.out.println("Stored messages saved to stored_messages.json.");
                } catch (IOException e) {
                    System.out.println("Error saving messages: " + e.getMessage());
                }
            }
        }

        input.close();
    }
}