package terapie;

import java.io.Serializable;

public enum TrvaniTerapie implements Serializable{
    KRATKA("krátka terapie", 2), DLOUHA("dlouhá terapie", 4);

    private final String nazev;
    private final int trvani;

    private TrvaniTerapie(String nazev, int trvani) {
        this.nazev = nazev;
        this.trvani = trvani;
    }

    public int getTrvani() {
        return trvani;
    }

    public String getNazev() {
        return nazev;
    }

    @Override
    public String toString() {
        return nazev + ":" + trvani + 'h';
    }

}
