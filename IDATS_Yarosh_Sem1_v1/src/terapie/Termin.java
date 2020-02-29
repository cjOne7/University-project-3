package terapie;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Termin implements Serializable {

    public static final Termin EMPTY_TERM = new Termin();

    private final Terapie terapie;
    private final TrvaniTerapie trvani;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Termin(final Terapie terapie, final TrvaniTerapie trvani, final LocalDateTime start, final LocalDateTime end) {
        this.terapie = terapie;
        this.trvani = trvani;
        this.start = start;
        this.end = end;
    }

    private Termin() {
        terapie = null;
        trvani = null;
        start = null;
        end = null;
    }

    public Terapie getTerapie() {
        return terapie;
    }

    public TrvaniTerapie getTrvani() {
        return trvani;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "Termin{"
                + "terapie=" + terapie
                + ", trvani=" + trvani
                + ", start=" + start.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                + ", end=" + end.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
                + '}';
    }
}
