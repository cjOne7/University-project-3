package terapie;

public class PracovniDoba {

    public static final PracovniDoba STANDARDNI_DOBA = new PracovniDoba(8, 16);

    private final int zacatek;
    private final int konec;

    public PracovniDoba(final int zacatek, final int konec) {
        this.zacatek = zacatek;
        this.konec = konec;
    }

    public int getZacatek() {
        return zacatek;
    }

    public int getKonec() {
        return konec;
    }

    public int getTrvani() {
        return konec - zacatek;
    }

    public boolean jeVDobe(final int hour) {
        return zacatek <= hour && hour <= konec;
    }

    @Override
    public String toString() {
        return "PracovniDoba{" + "zacatek=" + zacatek + ", konec=" + konec + '}';
    }

}
