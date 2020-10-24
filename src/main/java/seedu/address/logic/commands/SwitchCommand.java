package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Switch module list either from semester 1 to semester 2 or otherwise.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Switch working module list to the other semester"
            + "\nExample: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Switched module list ";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model = model.switchSemester();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchCommand); // instanceof handles nulls
    }
}
