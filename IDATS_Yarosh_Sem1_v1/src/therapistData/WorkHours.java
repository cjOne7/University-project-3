package therapistData;

public final class WorkHours {

    public static final WorkHours STANDARD_WORK_HOURS = new WorkHours(8, 16);

    private final int beginOfWorkDay;
    private final int endOfWorkDay;

    public WorkHours(final int begin, final int end) {
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

    @Override
    public String toString() {
        return "Working day starts at: " + beginOfWorkDay + "h, end at: " + endOfWorkDay + "h.";
    }
}
