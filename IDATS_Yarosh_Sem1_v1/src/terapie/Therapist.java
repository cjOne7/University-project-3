package terapie;

public class Therapist {

    private Person person;
    private WorkHours workHours;
    
    public static final Therapist EMPTY_THERAPIST = new Therapist(Person.EMPTY_PERSON, WorkHours.STANDARD_WORK_HOURS);

    public Therapist(final Person person, final WorkHours workHours) {
        this.person = person;
        this.workHours = workHours;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(final Person person) {
        this.person = person;
    }

    public WorkHours getWorkHours() {
        return workHours;
    }

    public void setWorkHours(final WorkHours workHours) {
        this.workHours = workHours;
    }

    @Override
    public String toString() {
        return "Therapist:\n\t" + person + "\n\t" + workHours;
    }
}
