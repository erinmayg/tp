package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedModule> modules1 = new ArrayList<>();
    private final List<JsonAdaptedModule> modules2 = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and modules.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("modules1") List<JsonAdaptedModule> modules1,
                                       @JsonProperty("modules2") List<JsonAdaptedModule> modules2) {
        if (persons != null) {
            this.persons.addAll(persons);
        }

        if (modules1 != null) {
            this.modules1.addAll(modules1);
        }

        if (modules2 != null) {
            this.modules2.addAll(modules2);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        modules1.addAll(source.getModuleList(0).getInternalList().stream()
                .map(JsonAdaptedModule::new).collect(Collectors.toList()));
        modules2.addAll(source.getModuleList(1).getInternalList().stream()
                .map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }

        for (JsonAdaptedModule jsonAdaptedModule : modules1) {
            Module module = jsonAdaptedModule.toModelType();
            if (addressBook.hasModule(module, 0)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            addressBook.addModule(module, 0);
        }

        for (JsonAdaptedModule jsonAdaptedModule : modules2) {
            Module module = jsonAdaptedModule.toModelType();
            if (addressBook.hasModule(module, 1)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            addressBook.addModule(module, 1);
        }
        return addressBook;
    }

}
