package terapie;

public class PracovniDoba {

    public static final PracovniDoba STANDARD_WORK_DAY = new PracovniDoba(8, 16);

    private final int beginOfWorkDay;
    private final int endOfWorkDay;

    public PracovniDoba(final int begin, final int end) {
        this.beginOfWorkDay = begin;
        this.endOfWorkDay = end;
    }

    public int getBeginOfWorkDay() {
        return beginOfWorkDay;
    }

    public int getEndOfWorkDay() {
        return endOfWorkDay;
    }

    public int getDurOfWorkDay() {
        return endOfWorkDay - beginOfWorkDay;
    }

    public boolean jeVDobe(final int hour) {
        return beginOfWorkDay <= hour && hour <= endOfWorkDay;
    }

    @Override
    public String toString() {
        return "PracovniDoba{" + "begin of work day: " + beginOfWorkDay + ", end of work day: " + endOfWorkDay + '}';
    }
}
