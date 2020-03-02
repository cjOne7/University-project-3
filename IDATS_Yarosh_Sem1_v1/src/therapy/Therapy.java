package therapy;

import java.io.Serializable;

public enum Therapy implements Serializable{
    HYPOTERAPIE("Hypoterapie"),
    CANISTERAPIE("Canisterapie"),
    ARTETERAPIE("Arteterapie"),
    MUZIKOTERAPIE("Muzikoterapie"),
    AQUATERAPIE("Aquaterapie"),
    REFLEXTERAPIE("Reflexoterapie");
    
    private final String nazev;

    private Therapy(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String toString() {
        return nazev;
    }

}
