package therapistData;

public final class Therapist {

    private final Person person;
    private final WorkHours workHours;

    public static final Therapist EMPTY_THERAPIST = new Therapist(Person.EMPTY_PERSON, WorkHours.STANDARD_WORK_HOURS);

    public Therapist(final Person person, final WorkHours workHours) {
        this.person = person;
        this.workHours = workHours;
    }

    public Person getPerson() {
        return person;
    }

    public WorkHours getWorkHours() {
        return workHours;
    }

    @Override
    public String toString() {
        return "Therapist:\n\t" + person + "\n\t" + workHours;
    }
}
