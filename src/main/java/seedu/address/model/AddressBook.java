package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueModuleList[] modules;
    private final UniqueModuleList displayModules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        modules = new UniqueModuleList[2];
        modules[0] = new UniqueModuleList();
        modules[1] = new UniqueModuleList();
        displayModules = new UniqueModuleList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(UniqueModuleList modules1, UniqueModuleList modules2) {
        this.modules[0].setModules(modules1);
        this.modules[1].setModules(modules2);
        this.displayModules.setModules(modules1);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setModules(newData.getModuleList(0), newData.getModuleList(1));
    }

    /*public void switchSemester() {
        this.semester = semester == 0 ? 1 : 0;
    }*/


    //// person-level operations
    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    public void clearContacts() {
        persons.clearAll();
    }

    /**
     * Removes the module with the specified {@code moduleCode} from this {@code AddressBook}.
     * Module with the {@code moduleCode} must exist in the address book.
     */
    public void removeModule(ModuleCode moduleCode, int semester) {
        modules[semester].removeModuleWithCode(moduleCode);
        displayModules.removeModuleWithCode(moduleCode);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeModule(Module key, int semester) {
        modules[semester].remove(key);
        displayModules.remove(key);
    }

    /**
     * Removes all the modules from the list.
     */
    public void clearMod( int semester ) {
        modules[semester].clearAll();
        displayModules.clearAll();
    }

    //// module-level operations
    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    public boolean hasModule(Module module, int semester) {
        requireNonNull(module);
        return modules[semester].contains(module);
    }

    /**
     * Returns true if a module with the same module code as {@code moduleCode} exists in the address book.
     */
    public boolean hasModuleCode(ModuleCode moduleCode, int semester) {
        requireNonNull(moduleCode);
        return modules[semester].containsModuleCode(moduleCode);
    }

    /**
     * Adds a module to the address book.
     * The module must not already exist in the address book.
     */
    public void addModule(Module m, int semester) {
        modules[semester].add(m);
        displayModules.add(m);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedModule} must not be the same as another existing person in the address book.
     */
    public void setModule(Module target, Module editedModule, int semester) {
        requireAllNonNull(target, editedModule);

        modules[semester].setModule(target, editedModule);
        displayModules.setModule(target,editedModule);
    }

    /**
     * Assigns an {@code instructor} to the module with the given {@code moduleCode}.
     * The module with the {@code moduleCode} must exist in the address book.
     */
    public void assignInstructor(Person instructor, ModuleCode moduleCode , int semester) {
        requireAllNonNull(instructor, moduleCode);

        modules[semester].assignInstructor(instructor, moduleCode);
        displayModules.assignInstructor(instructor, moduleCode);
    }

    public void unassignAllInstructors(int semester) {
        modules[semester].unassignAllInstructors();
        displayModules.unassignAllInstructors();
    }

    /**
     * Unassigns an {@code instructor} from the module with the given {@code moduleCode}.
     * The module with the {@code moduleCode} must exist in the address book.
     */
    public void unassignInstructor(Person instructor, ModuleCode moduleCode, int semester) {
        requireAllNonNull(instructor, moduleCode);

        modules[semester].unassignInstructor(instructor, moduleCode);
        displayModules.unassignInstructor(instructor, moduleCode);
    }

    /**
     * Checks whether an {@code instructor} in the module with the given {@code moduleCode} exists.
     * The module with the {@code moduleCode} must exist in the address book.
     * @return true if the {@code instructor} is an instructor of the module with the {@code moduleCode}
     */
    public boolean moduleCodeHasInstructor(ModuleCode moduleCode, Person instructor, int semester) {
        requireAllNonNull(instructor, moduleCode);
        return modules[semester].moduleCodeHasInstructor(moduleCode, instructor);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    public UniqueModuleList getModuleList(int semester) {
        // Currently no support for "contains" method for observable list
        return modules[semester];
    }

    public UniqueModuleList getDisplayModuleList() {
        return displayModules;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && modules[0].equals(((AddressBook) other).modules[0])
                && modules[1].equals(((AddressBook) other).modules[1])
                && displayModules.equals(((AddressBook) other).displayModules));
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons.hashCode(), modules.hashCode());
    }
}
