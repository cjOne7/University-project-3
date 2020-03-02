package therapy;

public class Person {

    private String name;
    private String surname;

    public static final Person EMPTY_PERSON = new Person("", "");

    public Person(final String name, final String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Person: name: " + name + ", surname: " + surname + ".";
    }
}
