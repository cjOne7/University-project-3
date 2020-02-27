package terapie;

public class Terapeut {

    private Person person;
    private PracovniDoba pracovniDoba;
    
    public static final Terapeut EMPTY_TERAPEUT = new Terapeut(Person.EMPTY_PERSON, PracovniDoba.STANDARDNI_DOBA);

    public Terapeut(final Person person, final PracovniDoba pracovniDoba) {
        this.person = person;
        this.pracovniDoba = pracovniDoba;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public PracovniDoba getPracovniDoba() {
        return pracovniDoba;
    }

    public void setPracovniDoba(PracovniDoba pracovniDoba) {
        this.pracovniDoba = pracovniDoba;
    }

    @Override
    public String toString() {
        return "Terapeut{" + "person=" + person + ", pracovniDoba=" + pracovniDoba + '}';
    }

}
