package terapie;

public class PracovniDoba {

    public static final PracovniDoba STANDARDNI_DOBA = new PracovniDoba(8, 16);

    private final int begin;
    private final int end;

    public PracovniDoba(final int begin, final int end) {
        this.begin = begin;
        this.end = end;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public int getTrvani() {
        return end - begin;
    }

    public boolean jeVDobe(final int hour) {
        return begin <= hour && hour <= end;
    }

    @Override
    public String toString() {
        return "PracovniDoba{" + "begin=" + begin + ", end=" + end + '}';
    }

}
