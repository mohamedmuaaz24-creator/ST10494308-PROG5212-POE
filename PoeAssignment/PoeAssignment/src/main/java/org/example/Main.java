package org.example;

import java.util.Scanner;

public class Main {
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

        Login newUser = new Login(username, password, firstName, lastName, phoneNumber);

        String registrationMessage = newUser.registerUser();
        System.out.println(registrationMessage);

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

        boolean loginStatus = newUser.loginUser(loginUsername, loginPassword);
        System.out.println(newUser.returnLoginStatus(loginStatus));

        // === QuickChat Phase ===
        if (loginStatus) {
            while (true) {
                System.out.println("\n=== QuickChat Menu ===");
                System.out.println("1) Send Message");
                System.out.println("2) Reports/Tools");
                System.out.println("3) Quit");
                System.out.print("Choose an option: ");

                int choice = Integer.parseInt(input.nextLine());

                if (choice == 1) {
                    System.out.print("Enter recipient cell number: ");
                    String recipient = input.nextLine();
                    System.out.print("Enter your message: ");
                    String messageText = input.nextLine();

                    Message msg = new Message(Message.sentMessages.size() + 1, recipient, messageText);

                    System.out.println("\nChoose an action:");
                    System.out.println("1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");
                    int action = Integer.parseInt(input.nextLine());

                    switch (action) {
                        case 1 -> {
                            Message.addSentMessage(msg);
                            System.out.println("Message successfully sent.");
                        }
                        case 2 -> {
                            Message.addDisregardedMessage(msg);
                            System.out.println("Message disregarded.");
                        }
                        case 3 -> {
                            Message.addStoredMessage(msg);
                            System.out.println("Message successfully stored.");
                        }
                        default -> System.out.println("Invalid action.");
                    }

                } else if (choice == 2) {
                    System.out.println("\n=== Reports/Tools ===");
                    System.out.println("1) Display Sent Messages Report");
                    System.out.println("2) Display Longest Sent Message");
                    System.out.println("3) Search by Message ID");
                    System.out.println("4) Search by Recipient");
                    System.out.println("5) Delete by Message Hash");
                    System.out.println("6) Back to Main Menu");

                    int toolChoice = Integer.parseInt(input.nextLine());

                    switch (toolChoice) {
                        case 1 -> Message.displaySentMessagesReport();
                        case 2 -> Message.displayLongestSentMessage();
                        case 3 -> {
                            System.out.print("Enter Message ID: ");
                            String id = input.nextLine();
                            Message.searchByMessageId(id);
                        }
                        case 4 -> {
                            System.out.print("Enter Recipient: ");
                            String rec = input.nextLine();
                            Message.searchByRecipient(rec);
                        }
                        case 5 -> {
                            System.out.print("Enter Message Hash: ");
                            String hash = input.nextLine();
                            Message.deleteByMessageHash(hash);
                        }
                        case 6 -> System.out.println("Returning to main menu...");
                        default -> System.out.println("Invalid option.");
                    }

                } else if (choice == 3) {
                    System.out.println("Exiting QuickChat. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid option. Try again.");
                }
            }
        }

        input.close();
    }
}
