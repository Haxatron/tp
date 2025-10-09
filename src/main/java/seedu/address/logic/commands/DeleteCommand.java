package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes one or more persons identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX [MORE_INDEXES] (must be positive integers)\n"
            + "Examples:\n"
            + COMMAND_WORD + " 1\n"
            + COMMAND_WORD + " 1 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_MULTIPLE_PERSON_SUCCESS = "Deleted Persons:\n%1$s";

    private final List<Index> targetIndexes;

    public DeleteCommand(List<Index> targetIndexes) {
        requireNonNull(targetIndexes);
        if (targetIndexes.isEmpty()) {
            throw new IllegalArgumentException("targetIndexes cannot be empty");
        }
        this.targetIndexes = List.copyOf(targetIndexes);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> personsToDelete = new ArrayList<>();
        for (Index targetIndex : targetIndexes) {
            personsToDelete.add(mapIndexToPerson(targetIndex, lastShownList));
        }

        Set<Person> uniquePersonsToDelete = new LinkedHashSet<>(personsToDelete);
        uniquePersonsToDelete.forEach(model::deletePerson);
        return new CommandResult(buildSuccessMessage(uniquePersonsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndexes.equals(otherDeleteCommand.targetIndexes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndexes", targetIndexes)
                .toString();
    }

    private Person mapIndexToPerson(Index targetIndex, List<Person> lastShownList) throws CommandException {
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(targetIndex.getZeroBased());
    }

    private String buildSuccessMessage(Collection<Person> personsToDelete) {
        if (personsToDelete.size() == 1) {
            Person person = personsToDelete.iterator().next();
            return String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(person));
        }

        String deletedPersons = personsToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));

        return String.format(MESSAGE_DELETE_MULTIPLE_PERSON_SUCCESS, deletedPersons);
    }
}
