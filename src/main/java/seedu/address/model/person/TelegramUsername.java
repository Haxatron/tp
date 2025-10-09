package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Telegram username in TAConnect.
 * Guarantees: immutable; is valid as declared in {@link #isValidTelegramUsername(String)}
 */
public class TelegramUsername {

    public static final String MESSAGE_CONSTRAINTS = "Telegram username should meet the following constraints:\n"
            + "1. Must be 5 to 32 characters long.\n"
            + "2. Accepted characters: a-z, A-Z, 0-9 and underscores.\n"
            + "3. Optionally, include @ as the first character.\n"
            + "For more details, see the following:"
            + "https://core.telegram.org/method/account.updateUsername#parameters\n";
    public static final String VALIDATION_REGEX = "^@?(?=.{5,32}$)[A-Za-z0-9_]+$";

    public final String value;

    /**
     * Constructs a {@code TelegramUsername}.
     *
     * @param telegramUsername A valid Telegram username.
     */
    public TelegramUsername(String telegramUsername) {
        requireNonNull(telegramUsername);
        checkArgument(isValidTelegramUsername(telegramUsername), MESSAGE_CONSTRAINTS);

        // include @ as the first character if not already included.
        this.value = (telegramUsername.startsWith("@")) ? telegramUsername : "@" + telegramUsername;
    }

    /**
     * Returns true if a given string is a valid session number.
     */
    public static boolean isValidTelegramUsername(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TelegramUsername)) {
            return false;
        }

        TelegramUsername otherTelegramUsername = (TelegramUsername) other;

        // Telegram usernames are case-insensitive, so lower-case for comparison
        return value.toLowerCase().equals(otherTelegramUsername.value.toLowerCase());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
