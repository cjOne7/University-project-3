package therapy;

import java.io.Serializable;

public enum DurOfTherapy implements Serializable {
    SHORT("short therapy", 2), LONG("long therapy", 4);

    private final String typeOfDuration;
    private final int durationOfTherapy;

    private DurOfTherapy(final String typeOfDuration, final int durationOfTherapy) {
        this.typeOfDuration = typeOfDuration;
        this.durationOfTherapy = durationOfTherapy;
    }

    public int getDurOfTherapy() {
        return durationOfTherapy;
    }

    public String getTypeOfDuration() {
        return typeOfDuration;
    }

    @Override
    public String toString() {
        return durationOfTherapy + "h";
    }
}
