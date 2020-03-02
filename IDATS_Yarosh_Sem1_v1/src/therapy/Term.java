package therapy;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Term implements Serializable {

    public static final Term EMPTY_TERM = new Term();

    private final Therapy therapy;
    private final DurOfTherapy durOfTherapy;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Term(final Therapy therapy, final DurOfTherapy trvani, final LocalDateTime start, final LocalDateTime end) {
        this.therapy = therapy;
        this.durOfTherapy = trvani;
        this.start = start;
        this.end = end;
    }

    private Term() {
        therapy = null;
        durOfTherapy = null;
        start = null;
        end = null;
    }

    public Therapy getTherapy() {
        return therapy;
    }

    public DurOfTherapy getDurOfTerm() {
        return durOfTherapy;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Therapy: " + therapy
                + ", duration of therapy: " + durOfTherapy
                + ", start of therapy: " + start.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                + ", end of therapy: " + end.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
}
