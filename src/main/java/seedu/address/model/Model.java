package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if contact list is empty.
     */
    boolean isEmptyPersonList();

    void switchSemester();

    int getSemester();

    /**
     * Clear all modules from the contact list.
     * The list must have at least one contact.
     */
    void clearContacts();

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Clear all modules from the module list.
     * The list must have at least one module.
     */
    void clearMod();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    boolean hasModule(Module module);

    /**
     * Returns true if a module has a module code of {@code moduleCode} exists in the address book.
     */
    boolean hasModuleCode(ModuleCode moduleCode);

    /**
     * Returns true if module list is empty.
     */
    boolean isEmptyModuleList();

    /**
     * Adds the given module.
     * {@code module} must not already exist in the address book.
     */
    void addModule(Module module);

    /**
     * Removes the module with the given {@code moduleCode}.
     * Module with the {@code moduleCode} must exist in the address book.
     */
    void deleteModule(ModuleCode moduleCode);

    /**
     * Assigns an {@code instructor} to the module with the given {@code moduleCode}.
     * The module with the {@code moduleCode} must exist in the address book.
     */
    void assignInstructor(Person instructor, ModuleCode moduleCode);

    void unassignAllInstructors();

    /**
     * Unassigns an {@code instructor} from the module with the given {@code moduleCode}.
     * The module with the {@code moduleCode} must exist in the address book.
     */
    void unassignInstructor(Person instructor, ModuleCode moduleCode);

    boolean moduleCodeHasInstructor(ModuleCode moduleCode, Person instructor);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

}
