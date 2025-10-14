package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Type(null));
    }

    @Test
    public void constructor_invalidType_throwsIllegalArgumentException() {
        String invalidType = "random";
        assertThrows(IllegalArgumentException.class, () -> new Type(invalidType));
    }

    @Test
    public void isValidType() {
        // null type
        assertThrows(NullPointerException.class, () -> Type.isValidType(null));

        // invalid types
        assertFalse(Type.isValidType("")); // empty string
        assertFalse(Type.isValidType(" ")); // spaces only
        assertFalse(Type.isValidType("friend"));
        assertFalse(Type.isValidType("tutor"));

        // valid types (case insensitive)
        assertTrue(Type.isValidType("student"));
        assertTrue(Type.isValidType("ta"));
        assertTrue(Type.isValidType("instructor"));
        assertTrue(Type.isValidType("staff"));
        assertTrue(Type.isValidType("STUDENT")); // uppercase still valid
    }

    @Test
    public void equals() {
        Type type = new Type("student");

        // same values -> returns true
        assertTrue(type.equals(new Type("student")));

        // same object -> returns true
        assertTrue(type.equals(type));

        // null -> returns false
        assertFalse(type.equals(null));

        // different types -> returns false
        assertFalse(type.equals(5.0f));

        // different values -> returns false
        assertFalse(type.equals(new Type("ta")));
    }

    @Test
    public void isType() {
        Type type1 = new Type("student");
        Type type2 = new Type("ta");
        Type type3 = new Type("instructor");
        Type type4 = new Type("staff");

        assertTrue(type1.isStudent());
        assertTrue(type2.isTa());
        assertTrue(type3.isInstructor());
        assertTrue(type4.isStaff());

        assertFalse(type1.isInstructor());
        assertFalse(type2.isStudent());
        assertFalse(type3.isStaff());
        assertFalse(type4.isTa());
    }
}
