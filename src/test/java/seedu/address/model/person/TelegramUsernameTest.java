package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TelegramUsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TelegramUsername(null));
    }

    @Test
    public void constructor_invalidTelegramUsername_throwsIllegalArgumentException() {
        String invalidTelegramUsername = "";
        assertThrows(IllegalArgumentException.class, () -> new TelegramUsername(invalidTelegramUsername));
    }

    @Test
    public void isValidTelegramUsername() {
        // null telegram username
        assertThrows(NullPointerException.class, () -> TelegramUsername.isValidTelegramUsername(null));

        // invalid telegram usernames
        assertFalse(TelegramUsername.isValidTelegramUsername("")); // empty string
        assertFalse(TelegramUsername.isValidTelegramUsername("AAAA")); // less than 5 characters
        assertFalse(TelegramUsername.isValidTelegramUsername("A".repeat(33))); // more than 32 characters
        assertFalse(TelegramUsername.isValidTelegramUsername("*#%&^")); // invalid characters
        assertFalse(TelegramUsername.isValidTelegramUsername("AAAA@")); // @ after the first character

        // valid telegram usernames
        assertTrue(TelegramUsername.isValidTelegramUsername("ABC12")); // 5 letters
        assertTrue(TelegramUsername.isValidTelegramUsername("@ABC12")); // @ as the first character
        assertTrue(TelegramUsername.isValidTelegramUsername("@" + "A".repeat(32))); // @ and 32 characters
        assertTrue(TelegramUsername.isValidTelegramUsername("@_____")); // underscores
    }

    @Test
    public void equals() {
        TelegramUsername telegramUsername = new TelegramUsername("@TestUser");

        // same values -> returns true
        assertTrue(telegramUsername.equals(new TelegramUsername("@TestUser")));

        // same object -> returns true
        assertTrue(telegramUsername.equals(telegramUsername));

        // null -> returns false
        assertFalse(telegramUsername.equals(null));

        // different case -> returns true
        assertFalse(telegramUsername.equals("@tEStUsER"));

        // exclude @ -> returns true
        assertFalse(telegramUsername.equals("TestUser"));

        // different values -> returns false
        assertFalse(telegramUsername.equals(new TelegramUsername("@TestUser2")));
    }
}
