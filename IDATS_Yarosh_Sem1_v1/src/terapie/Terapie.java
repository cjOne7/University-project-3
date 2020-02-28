package terapie;

import java.io.Serializable;

public enum Terapie implements Serializable{
    HYPOTERAPIE("Hypoterapie"),
    CANISTERAPIE("Canisterapie"),
    ARTETERAPIE("Arteterapie"),
    MUZIKOTERAPIE("Muzikoterapie"),
    AQUATERAPIE("Aquaterapie"),
    REFLEXTERAPIE("Reflexoterapie");
    
    private final String nazev;

    private Terapie(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String toString() {
        return nazev;
    }

}
