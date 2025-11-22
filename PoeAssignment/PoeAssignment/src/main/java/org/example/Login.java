package org.example;

public class Login {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public Login(String username, String password, String firstName, String lastName, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public boolean checkUserName() {
        return (username.contains("_") && username.length() <= 5);
    }

    public boolean checkPasswordComplexity() {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        boolean longEnough = password.length() >= 8;
        return (hasUppercase && hasNumber && hasSpecialChar && longEnough);
    }

    public boolean checkCellPhoneNumber() {
        return phoneNumber.matches("^\\+27\\d{9,10}$");
    }

    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }
        return "User registered successfully!";
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return (this.username.equals(enteredUsername) && this.password.equals(enteredPassword));
    }

    public String returnLoginStatus(boolean loginStatus) {
        if (loginStatus) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
