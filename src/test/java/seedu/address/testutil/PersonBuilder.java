package seedu.address.testutil;

import java.util.Optional;

import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Session;
import seedu.address.model.person.TelegramUsername;
import seedu.address.model.person.Type;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_TYPE = "student";
    public static final String DEFAULT_TELEGRAM = "@amybee";
    public static final String DEFAULT_SESSION = "G1";

    private Name name;
    private Phone phone;
    private Email email;
    private Type type;
    private Optional<TelegramUsername> telegramUsername;
    private Optional<Session> session;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        type = new Type(DEFAULT_TYPE);
        telegramUsername = Optional.ofNullable(new TelegramUsername(DEFAULT_TELEGRAM));
        session = Optional.ofNullable(new Session(DEFAULT_SESSION));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        type = personToCopy.getType();
        telegramUsername = personToCopy.getTelegramUsername();
        session = personToCopy.getSession();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code Person} that we are building.
     */
    public PersonBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code Person} that we are building.
     */
    public PersonBuilder withTelegram(String telegram) {
        if (telegram == null || telegram.isBlank()) {
            this.telegramUsername = Optional.empty();
        } else {
            this.telegramUsername = Optional.of(new TelegramUsername(telegram));
        }
        return this;
    }

    /**
     * Sets the {@code Session} of the {@code Person} that we are building.
     */
    public PersonBuilder withSession(String session) {
        if (session == null || session.isBlank()) {
            this.session = Optional.empty();
        } else {
            this.session = Optional.of(new Session(session));
        }
        return this;
    }

    /**
     * Builds a Person based on type/session constraints.
     */
    public Person build() {
        // Handle constraints according to Person.java rules
        if (type.isStudent() || type.isTa()) {
            return new Person(name, phone, email, type, telegramUsername.orElse(null),
                    session.orElseThrow(() -> new IllegalArgumentException(Person.MESSAGE_STUDENT_TA)));
        } else {
            // Instructors and staff cannot have sessions
            return new Person(name, phone, email, type,
                    telegramUsername.orElse(null), null);
        }
    }

}
