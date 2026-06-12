package com.mycompany.userlogin;

import java.util.Scanner;

/*
 * @author LEBOGANG.F MOKGETHOA
 * @version 1.1
 */
public class Userlogin {

    // Instance variables
    String username;
    String password;
    String phone;
    String firstName;
    String lastName;

    // Constructor
    public Userlogin(String username, String password, String phone,
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

        System.out.println("---- Registration ----");

        System.out.print("First Name: ");
        String fName = input.nextLine();

        System.out.print("Last Name: ");
        String lName = input.nextLine();

        System.out.print("Username (needs _ and max 5 chars): ");
        String uName = input.nextLine();

        System.out.print("Password: ");
        String pass = input.nextLine();

        System.out.print("Phone (+27): ");
        String cell = input.nextLine();

        // Create user object
        Userlogin newUser = new Userlogin(uName, pass, cell, fName, lName);

        // Register and print result
        String status = newUser.registerUser();
        System.out.println("\nRegistration Result: " + status);

        // Only proceed to login if registration succeeded
        if (status.equals("Registration successful!")) {
            System.out.println("\n---- Login ----");

            System.out.print("Enter username: ");
            String loginU = input.nextLine();

            System.out.print("Enter Password: ");
            String loginP = input.nextLine();

            boolean success = newUser.loginUser(loginU, loginP);
            System.out.println(newUser.returnLoginStatus(success));

            // FIX: launch QuickChat after successful login
            if (success) {
                Message.quickChat();
            }
        }

        input.close(); // FIX: close Scanner to prevent resource leak
    }

    // --- Validation Methods (required by JUnit tests) ---

    /**
     * Checks username contains underscore AND is 5 chars or fewer.
     * @return true if valid
     */
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks password is at least 8 chars and has a capital,
     * a digit, and a special character.
     * @return true if valid
     */
    public boolean checkPasswordComplexity() {
        if (password.length() < 8) return false;
        boolean hasCapital = password.matches(".*[A-Z].*");
        boolean hasDigit   = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        return hasCapital && hasDigit && hasSpecial;
    }

    /**
     * Checks phone starts with +27 and has exactly 9 digits after.
     * Total length must be 12 characters.
     * @return true if valid
     */
    public boolean checkCellPhoneNumber() {
        return phone.startsWith("+27")
                && phone.length() == 12
                && phone.substring(3).matches("\\d{9}");
    }

    // --- Registration ---

    public String registerUser() {
        // Check username
        if (!checkUserName()) {
            if (!username.contains("_")) {
                return "Username must contain an underscore (_)";
            }
            return "Username must not have more than 5 characters";
        }

        // Check password complexity
        if (!checkPasswordComplexity()) {
            if (password.length() < 8) {
                return "Password must be at least 8 characters long";
            }
            return "Password must have a capital letter, numbers, and special characters";
        }

        // FIX: full phone validation — not just +27 prefix
        if (!checkCellPhoneNumber()) {
            return "Phone number must start with +27 and be 12 digits total";
        }

        return "Registration successful!";
    }

    // --- Login ---

    public boolean loginUser(String enteredUser, String enteredPass) {
        return enteredUser.equals(username) && enteredPass.equals(password);
    }

    public String returnLoginStatus(boolean success) {
        if (success) {
            // FIX: added space and capital "It" after the period
            return "Welcome " + firstName + " " + lastName + ". It is great to see you again.";
        } else {
            // FIX: added space after "failed."
            return "Login failed. Please try again.";
        }
    }
}
