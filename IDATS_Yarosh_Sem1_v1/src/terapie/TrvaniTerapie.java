package terapie;

import java.io.Serializable;

public enum TrvaniTerapie implements Serializable {
    SHORT("short therapy", 2), LONG("long therapy", 4);

    private final String typeOfDuration;
    private final int durationOfTherapy;

    private TrvaniTerapie(final String typeOfDuration, final int durationOfTherapy) {
        this.typeOfDuration = typeOfDuration;
        this.durationOfTherapy = durationOfTherapy;
    }

    public int getDurationOfTherapy() {
        return durationOfTherapy;
    }

    public String getTypeOfDuration() {
        return typeOfDuration;
    }

    @Override
    public String toString() {
        return typeOfDuration + ':' + durationOfTherapy + 'h';
    }
}
