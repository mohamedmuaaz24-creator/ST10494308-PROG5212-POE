package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    // ---------- Username Tests ----------

    @Test
    public void testCorrectUsernameFormatting() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertEquals("User registered successfully!", user.registerUser());
    }

    @Test
    public void testIncorrectUsernameFormatting() {
        Login user = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertEquals(
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.",
                user.registerUser()
        );
    }

    // ---------- Password Tests ----------

    @Test
    public void testPasswordMeetsComplexity() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testPasswordDoesNotMeetComplexity() {
        Login user = new Login("kyl_1", "password", "John", "Doe", "+27838968976");
        assertFalse(user.checkPasswordComplexity());
    }

    // ---------- Cell Phone Tests ----------

    @Test
    public void testCellPhoneCorrectlyFormatted() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneIncorrectlyFormatted() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "08966553");
        assertFalse(user.checkCellPhoneNumber());
    }

    // ---------- Login Authentication Tests ----------

    @Test
    public void testLoginSuccessful() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertFalse(user.loginUser("kyl_1", "wrongPass123"));
    }

    // ---------- Username Boolean Checks ----------

    @Test
    public void testUsernameCorrectlyFormattedTrue() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(user.checkUserName());
    }

    @Test
    public void testUsernameIncorrectlyFormattedFalse() {
        Login user = new Login("kyle!!!!!!!", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertFalse(user.checkUserName());
    }

    // ---------- Password Boolean Checks ----------

    @Test
    public void testPasswordComplexityTrue() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testPasswordComplexityFalse() {
        Login user = new Login("kyl_1", "password", "John", "Doe", "+27838968976");
        assertFalse(user.checkPasswordComplexity());
    }

    // ---------- Cell Phone Boolean Checks ----------

    @Test
    public void testCellPhoneNumberTrue() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");
        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCellPhoneNumberFalse() {
        Login user = new Login("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "08966553");
        assertFalse(user.checkCellPhoneNumber());
    }

}