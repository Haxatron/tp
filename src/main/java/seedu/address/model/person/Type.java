package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's contact type in the address book.
 * Valid types: student, ta, instructor, staff.
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS =
            "The content must be “student”, “ta”, “instructor”, or “staff” in the tag field.";
    public static final String VALIDATION_REGEX = "(?i)(student|ta|instructor|staff)";

    public final String value;

    /**
     * Constructs a {@code Type}.
     *
     * @param type A valid contact type.
     */
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_CONSTRAINTS);
        this.value = type.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
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
        if (!(other instanceof Type)) {
            return false;
        }

        Type otherType = (Type) other;
        return value.equals(otherType.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
