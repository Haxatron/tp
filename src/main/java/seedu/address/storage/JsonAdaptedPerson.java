package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Session;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.person.Type;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String type;
    private final String telegramUsername;
    private final String session;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("type") String type,
            @JsonProperty("telegramUsername") String telegramUsername, @JsonProperty("session") String session) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.type = type;
        this.telegramUsername = telegramUsername;
        this.session = session;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        type = source.getType().value;
        telegramUsername = source.getTelegramUsername().isPresent()
                ? source.getTelegramUsername().get().value
                : null;
        session = source.getSession().isPresent()
                ? source.getSession().get().toString()
                : null;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        final TelegramUsername modelTelegram;
        if (telegramUsername == null) {
            modelTelegram = null;
        } else {
            if (!TelegramUsername.isValidTelegramUsername(telegramUsername)) {
                throw new IllegalValueException(TelegramUsername.MESSAGE_CONSTRAINTS);
            }
            modelTelegram = new TelegramUsername(telegramUsername);
        }

        final Session modelSession;
        if (modelType.isStudent() || modelType.isTa()) {
            if (session == null) {
                throw new IllegalValueException(Person.MESSAGE_STUDENT_TA);
            }
            if (!Session.isValidSession(session)) {
                throw new IllegalValueException(Session.MESSAGE_CONSTRAINTS);
            }
            modelSession = new Session(session);
        } else {
            if (session != null) {
                throw new IllegalValueException(Person.MESSAGE_INSTRUCTOR_STAFF);
            }
            modelSession = null;
        }

        return new Person(modelName, modelPhone, modelEmail, modelType, modelTelegram, modelSession);
    }

}
