package sprava;

import therapy.Serializer;
import colection.AbstrDoubleList;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.function.Consumer;
import therapistData.Therapist;
import therapy.Therapy;
import therapy.Term;
import therapy.DurOfTherapy;
import util.MaticeObsazenosti;
import util.Obdobi;
import therapy.GenerateTerms;

public class SpravaTerminu implements Sprava {

    private AbstrDoubleList<Term> listOfTerms;
    private Therapist terapeut;
    private Consumer<String> alert;
    private Consumer<String> logger;

    private GenerateTerms generateTerms = new GenerateTerms();

    private final Consumer<String> NULL_CONSUMER = s -> {
    };

    public SpravaTerminu(
            final Therapist terapeut,
            final Consumer<String> alert,
            final Consumer<String> logger) {
        this.listOfTerms = new AbstrDoubleList<>();
        this.terapeut = (terapeut == null) ? Therapist.EMPTY_THERAPIST : terapeut;
        this.alert = (alert == null) ? NULL_CONSUMER : alert;
        this.logger = (alert == null) ? NULL_CONSUMER : logger;
    }

    @Override
    public void vlozTermin(Term termin) throws SpravceException {
        listOfTerms.vlozPosledni(termin);
    }

    @Override
    public void vlozTermin(Term termin, Pozice pozice) throws SpravceException {
    }

    @Override
    public Term zpristupniTermin(Pozice pozice) throws SpravceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Term odeberTermin(Pozice pozice) throws SpravceException {
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
    public Term najdiPrvniVolnyTermin(Therapy terapie, DurOfTherapy trvani, LocalDate odkdy, LocalDate dokdy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void uloz(String fileName) throws SpravceException {
        Serializer.saveBinary(listOfTerms, fileName, generateTerms.getIsBusy());
    }

    @Override
    public void nacti(String fileName) throws SpravceException {
        Serializer.loadBinary(listOfTerms, fileName, generateTerms);
    }

    @Override
    public Therapist getTerapeut() {
        return terapeut;
    }

    @Override
    public void zrus() {
        listOfTerms.zrus();
    }

    @Override
    public Term najdiDalsiVolnyTermin(LocalDate odkdy, LocalDate dokdy) throws SpravceException {
        return Sprava.super.najdiDalsiVolnyTermin(odkdy, dokdy); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generuj(Obdobi obdobi, int pocetTerapii) {
        generateTerms.generateTerms(pocetTerapii, terapeut.getWorkHours(), obdobi.getDatumOdKdy(), obdobi.getDatumDoKdy());
    }

    @Override
    public void vlozTermíny(Term... terminy) {
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
    public Term najdiPrvniVolnyTermin(Therapy terapie, DurOfTherapy trvani, String odkdy, String dokdy) {
        return Sprava.super.najdiPrvniVolnyTermin(terapie, trvani, odkdy, dokdy); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Term najdiPrvniVolnyTermin(Therapy terapie, DurOfTherapy trvani, Obdobi obdobi) {
        return Sprava.super.najdiPrvniVolnyTermin(terapie, trvani, obdobi); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Term> iterator() {
        return new Iterator<Term>() {
            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Term next() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}
