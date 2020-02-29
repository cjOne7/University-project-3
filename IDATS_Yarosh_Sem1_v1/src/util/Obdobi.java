package util;

import java.time.LocalDate;
import java.time.Period;

public class Obdobi {

    public static final Obdobi NEXT_WEEK = new Obdobi(LocalDate.now(), LocalDate.now().plusDays(7));
    
    private LocalDate datumOdKdy;
    private LocalDate datumDoKdy;

    public Obdobi(final LocalDate datumOdkdy, final LocalDate datumDokdy) {
        this.datumOdKdy = datumOdkdy;
        this.datumDoKdy = datumDokdy;
    }

    public Obdobi(final String datumOdkdy, final String datumDokdy) {
        this.datumOdKdy = DateUtil.convert(datumOdkdy);
        this.datumDoKdy = DateUtil.convert(datumDokdy);
    }

    public LocalDate getDatumOdKdy() {
        return datumOdKdy;
    }

    public LocalDate getDatumDoKdy() {
        return datumDoKdy;
    }

    public void setDatumOdKdy(LocalDate datumOdKdy) {
        this.datumOdKdy = datumOdKdy;
    }

    public void setDatumDoKdy(LocalDate datumDoKdy) {
        this.datumDoKdy = datumDoKdy;
    }

    public int getPocetDnu() {
        return Period.between(datumOdKdy, datumDoKdy).getDays() + 1;
    }

    public boolean jeDatumVObdobi(final LocalDate date) {
        return (!date.isBefore(datumOdKdy)) && (!date.isAfter(datumDoKdy));
    }

    @Override
    public String toString() {
        return "Obdobi{" + "datumOdkdy=" + datumOdKdy + ", datumDokdy=" + datumDoKdy + '}';
    }

}
