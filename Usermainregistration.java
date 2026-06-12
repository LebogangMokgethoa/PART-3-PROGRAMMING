package com.mycompany.userlogin;

import java.util.Scanner;

/**
 * @author LEBOGANG.F MOKGETHOA
 * @version 1.0
 * Handles validation, registration, and login.
 */
public class Usermainregistration {

    // Instance variables
    String username;
    String password;
    String phone;
    String firstName;
    String lastName;

    // Constructor — all 5 fields required
    public Usermainregistration(String username, String password, String phone,
                                String firstName, String lastName) {
        this.username  = username;
        this.password  = password;
        this.phone     = phone;
        this.firstName = firstName;
        this.lastName  = lastName;
    }

    // Main method
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("----- Register New User -----");

        System.out.print("Enter First Name: ");
        String fName = input.nextLine();

        System.out.print("Enter Last Name: ");
        String lName = input.nextLine();

        System.out.print("Enter Username (must contain _ and be max 5 chars): ");
        String uName = input.nextLine();

        System.out.print("Enter Password: ");
        String passW = input.nextLine();

        // FIX: phone was never collected — added here
        System.out.print("Enter Phone Number (+27...): ");
        String cellNum = input.nextLine();

        // FIX: now passes all 5 arguments to match the constructor
        Usermainregistration newUser = new Usermainregistration(uName, passW, cellNum, fName, lName);

        // Run registration and print result
        String regStatus = newUser.registerUser();
        System.out.println("\n" + regStatus);

        // Only proceed to login if registration was successful
        if (regStatus.contains("successful")) {

            System.out.println("\n--- Login ---");

            System.out.print("Username: ");
            String loginUser = input.nextLine();

            // FIX: was println (cursor drops to next line) — changed to print
            System.out.print("Password: ");
            String loginPass = input.nextLine();

            boolean success = newUser.loginUser(loginUser, loginPass);

            System.out.println(newUser.returnLoginStatus(success));
        }

        input.close();
    }

    // Registration validation
    public String registerUser() {

        // Check for underscore in username
        if (!username.contains("_")) {
            return "Username must contain an underscore (_).";
        }

        // Check username length
        if (username.length() > 5) {
            return "Username must not be more than 5 characters.";
        }

        // FIX: was <= 8 (rejected valid 8-char passwords) — changed to < 8
        if (password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }

        // Fix: added missing complexity checks
        boolean hasCapital = password.matches(".*[A-Z].*");
        boolean hasDigit   = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        if (!hasCapital || !hasDigit || !hasSpecial) {
            return "Password must contain a capital letter, a number, and a special character.";
        }

        // Check phone number format
        if (!phone.startsWith("+27") || phone.length() != 12
                || !phone.substring(3).matches("\\d{9}")) {
            return "Phone number must start with +27 and be 12 digits total.";
        }

        return "Registration successful for " + firstName + " " + lastName + "!";
    }

    // Login check
    public boolean loginUser(String enteredUser, String enteredPass) {
        return enteredUser.equals(username) && enteredPass.equals(password);
    }

    // Login status message
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Login successful! Welcome " + firstName + " " + lastName + ".";
        } else {
            return "Login failed. Incorrect username or password.";
        }
    }
}