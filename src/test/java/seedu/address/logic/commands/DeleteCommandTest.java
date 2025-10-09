package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand.Selector;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_emptyIndexes_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new DeleteCommand(List.of()));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromIndex(INDEX_FIRST_PERSON)));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromIndex(outOfBoundIndex)));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleIndexesUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person thirdPerson = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromIndex(INDEX_FIRST_PERSON),
                Selector.fromIndex(INDEX_THIRD_PERSON)));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_PERSON_SUCCESS,
                Messages.format(firstPerson) + "\n" + Messages.format(thirdPerson));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPerson);
        expectedModel.deletePerson(thirdPerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateIndexesUnfilteredList_success() {
        Model actualModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = actualModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromIndex(INDEX_FIRST_PERSON),
                Selector.fromIndex(INDEX_FIRST_PERSON)));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(actualModel.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, actualModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNameUnfilteredList_success() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name targetName = targetPerson.getName();

        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromName(targetName)));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(targetPerson));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(targetPerson);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nameNotFound_throwsCommandException() {
        Name missingName = new Name("Non Existent");
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromName(missingName)));

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND_BY_NAME, missingName));
    }

    @Test
    public void execute_nameCaseMismatch_throwsCommandException() {
        Person targetPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Name incorrectCase = new Name(targetPerson.getName().toString().toLowerCase());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromName(incorrectCase)));

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND_BY_NAME, incorrectCase));
    }

    @Test
    public void execute_multipleNamesUnfilteredList_success() {
        Model startingModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person firstPerson = startingModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person secondPerson = startingModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(
                Selector.fromName(firstPerson.getName()),
                Selector.fromName(secondPerson.getName())));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_PERSON_SUCCESS,
                Messages.format(firstPerson) + "\n" + Messages.format(secondPerson));

        Model expectedModel = new ModelManager(startingModel.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPerson);
        expectedModel.deletePerson(secondPerson);

        assertCommandSuccess(deleteCommand, startingModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_mixedSelectorsUnfilteredList_success() {
        Model startingModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person firstPerson = startingModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person thirdPerson = startingModel.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(
                Selector.fromIndex(INDEX_FIRST_PERSON),
                Selector.fromName(thirdPerson.getName())));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MULTIPLE_PERSON_SUCCESS,
                Messages.format(firstPerson) + "\n" + Messages.format(thirdPerson));

        Model expectedModel = new ModelManager(startingModel.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(firstPerson);
        expectedModel.deletePerson(thirdPerson);

        assertCommandSuccess(deleteCommand, startingModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromIndex(INDEX_FIRST_PERSON)));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromIndex(outOfBoundIndex)));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(List.of(Selector.fromIndex(INDEX_FIRST_PERSON)));
        DeleteCommand deleteSecondCommand = new DeleteCommand(List.of(Selector.fromIndex(INDEX_SECOND_PERSON)));
        Name firstName = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getName();
        DeleteCommand deleteByName = new DeleteCommand(List.of(Selector.fromName(firstName)));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(List.of(Selector.fromIndex(INDEX_FIRST_PERSON)));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
        assertTrue(deleteByName.equals(new DeleteCommand(List.of(Selector.fromName(firstName)))));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
        DeleteCommand deleteDifferentName = new DeleteCommand(List.of(Selector.fromName(new Name("Another Name"))));
        assertFalse(deleteByName.equals(deleteDifferentName));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(Selector.fromIndex(targetIndex)));
        String expected = DeleteCommand.class.getCanonicalName()
                + "{selectors=["
                + DeleteCommand.Selector.class.getCanonicalName()
                + "{index=" + targetIndex + ", name=null}]}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
