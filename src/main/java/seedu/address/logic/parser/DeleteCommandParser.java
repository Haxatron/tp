package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCommand.Selector;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final Prefix PREFIX_NAME = new Prefix("n/");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        List<Selector> selectors = new ArrayList<>();

        parseIndexes(argMultimap.getPreamble(), selectors);
        parseNames(argMultimap.getAllValues(PREFIX_NAME), selectors);

        if (selectors.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(selectors);
    }

    private void parseIndexes(String preamble, List<Selector> selectors) throws ParseException {
        if (preamble == null || preamble.isBlank()) {
            return;
        }

        String[] tokens = preamble.trim().split("\\s+");

        for (String token : tokens) {
            if (token.isBlank()) {
                continue;
            }
            try {
                Index index = ParserUtil.parseIndex(token);
                selectors.add(Selector.fromIndex(index));
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
            }
        }
    }

    private void parseNames(List<String> nameValues, List<Selector> selectors) throws ParseException {
        for (String rawName : nameValues) {
            String trimmedName = rawName.trim();
            if (trimmedName.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            try {
                Name name = ParserUtil.parseName(trimmedName);
                selectors.add(Selector.fromName(name));
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
            }
        }
    }

}
