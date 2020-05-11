package therapy;

import java.io.Serializable;

public enum Therapy implements Serializable {
    HIPPOTHERAPY("Hippotherapy"),
    CANISTHERAPY("Canistherapy"),
    ART_THERAPY("Art therapy"),
    MUSIC_THERAPY("Music therapy"),
    AQUATHERAPY("Aquatherapy"),
    REFLEXTHERAPY("Reflextherapy");

    private final String name;

    private Therapy(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
