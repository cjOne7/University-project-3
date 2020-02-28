package sprava;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import terapie.Terapeut;
import terapie.Terapie;
import terapie.Termin;
import terapie.TrvaniTerapie;
import util.MaticeObsazenosti;
import util.Obdobi;
import kolekce.*;

public class SpravaTerminu implements Sprava {

    private AbstrDoubleList<Termin> seznamTerminu;
    private Terapeut terapeut;
    private Consumer<String> alert;
    private Consumer<String> logger;

    private final Consumer<String> NULL_CONSUMER = s -> {
    };

    public SpravaTerminu(
            final Terapeut terapeut,
            final Consumer<String> alert,
            final Consumer<String> logger) {
        this.seznamTerminu = new AbstrDoubleList<>();
        this.terapeut = (terapeut == null) ? Terapeut.EMPTY_TERAPEUT : terapeut;
        this.alert = (alert == null) ? NULL_CONSUMER : alert;
        this.logger = (alert == null) ? NULL_CONSUMER : logger;
    }

    @Override
    public void vlozTermin(Termin termin) throws SpravceException {
        if (jeVolno(termin.getStart(), termin.getEnd())) {

        }
    }

    @Override
    public void vlozTermin(Termin termin, Pozice pozice) throws SpravceException {
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
        Objects.requireNonNull(seznamTerminu);
        try (ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(new FileOutputStream(soubor))) {
            objectOutputStream.writeInt(seznamTerminu.getMohutnost());
            Iterator<Termin> it = seznamTerminu.iterator();
            while (it.hasNext()) {
                objectOutputStream.writeObject(it.next());
            }
        } catch (IOException ex) {
            new SpravceException("File " + soubor + " do not exist");
        }
    }

    @Override
    public void nacti(String soubor) throws SpravceException {
        Objects.requireNonNull(seznamTerminu);
        zrus();
        try (ObjectInputStream objectInputStream
                = new ObjectInputStream(new FileInputStream(soubor))) {
            int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
                Termin prvek = (Termin) objectInputStream.readObject();
                seznamTerminu.vlozPosledni(prvek);
            }
        } catch (IOException ex) {
            new SpravceException("File " + soubor + " do not exist");
        } catch (ClassNotFoundException ex) {
            new SpravceException("Class not found");
        }
    }

    @Override
    public Terapeut getTerapeut() {
        return terapeut;
    }

    @Override
    public void zrus() {
        seznamTerminu.zrus();
    }

    @Override
    public Termin najdiDalsiVolnyTermin(LocalDate odkdy, LocalDate dokdy) throws SpravceException {
        return Sprava.super.najdiDalsiVolnyTermin(odkdy, dokdy); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generuj(Obdobi obdobi, int pocetTerapii) {
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
        return new Iterator<Termin>() {
            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Termin next() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }
}
