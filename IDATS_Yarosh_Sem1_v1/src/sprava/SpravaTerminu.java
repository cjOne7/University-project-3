package sprava;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.function.Consumer;
import terapie.Terapeut;
import terapie.Terapie;
import terapie.Termin;
import terapie.TrvaniTerapie;
import util.MaticeObsazenosti;
import util.Obdobi;
import kolekce.*;

public class SpravaTerminu implements Sprava {

    private AbstrDoubleList seznamTerminu;
    private Terapeut terapeut;
    private Consumer<String> alert;
    private Consumer<String> logger;

    public SpravaTerminu(Terapeut terapeut, Consumer<String> alert, Consumer<String> logger) {
        this.seznamTerminu = new AbstrDoubleList<>();
        this.terapeut = /*(terapeut == null) ? Terapeut.EMPTY_TERAPEUT :*/ terapeut;
        this.alert = /*(alert == null) ? NULL_CONSUMER :*/ alert;
        this.logger = /*(alert == null) ? NULL_CONSUMER :*/ logger;
    }

    @Override
    public void vlozTermin(Termin termin) throws SpravceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void vlozTermin(Termin termin, Pozice pozice) throws SpravceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Termin zpristupniTermin(Pozice pozice) throws SpravceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Termin odeberTermin(Pozice pozice) throws SpravceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean jeVolno(LocalDateTime odKdy, LocalDateTime doKdy) throws SpravceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MaticeObsazenosti getObsazenost(LocalDate odKdy, LocalDate doKdy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Termin najdiPrvniVolnyTermin(Terapie terapie, TrvaniTerapie trvani, LocalDate odkdy, LocalDate dokdy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void uloz(String soubor) throws SpravceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nacti(String soubor) throws SpravceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Terapeut getTerapeut() {
        return terapeut;
    }

    @Override
    public void zrus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Termin najdiDalsiVolnyTermin(LocalDate odkdy, LocalDate dokdy) throws SpravceException {
        return Sprava.super.najdiDalsiVolnyTermin(odkdy, dokdy); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generuj(Obdobi obdobi, int pocetTerapii) {
        Sprava.super.generuj(obdobi, pocetTerapii); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void vlozTermíny(Termin... terminy) {
        Sprava.super.vlozTermíny(terminy); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MaticeObsazenosti getObsazenost(Obdobi obdobi) {
        return Sprava.super.getObsazenost(obdobi); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean jeVolno(String odKdy, String doKdy) throws SpravceException {
        return Sprava.super.jeVolno(odKdy, doKdy); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Termin najdiPrvniVolnyTermin(Terapie terapie, TrvaniTerapie trvani, String odkdy, String dokdy) {
        return Sprava.super.najdiPrvniVolnyTermin(terapie, trvani, odkdy, dokdy); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Termin najdiPrvniVolnyTermin(Terapie terapie, TrvaniTerapie trvani, Obdobi obdobi) {
        return Sprava.super.najdiPrvniVolnyTermin(terapie, trvani, obdobi); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Termin> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
