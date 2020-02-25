package sprava;

import util.Obdobi;
import util.MaticeObsazenosti;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import terapie.Terapeut;
import terapie.Terapie;
import terapie.Termin;
import terapie.TrvaniTerapie;
import util.DateTimeUtil;

/**
 * Konstruktor implementační třídy
 *
 * <pre>
 * <code>
 *  public SpravaTerminu(Terapeut terapeut,  Consumer<String> alert, Consumer<String> logger) {
 *      this.seznamTerminu = new AbstrDoubleList<>();
 *      this.terapeut = (terapeut == null) ? Terapeut.EMPTY_TERAPEUT : terapeut;
 *      this.alert = (alert == null) ? NULL_CONSUMER : alert;
 *      this.logger = (alert == null) ? NULL_CONSUMER : logger;
 * }
 * </code>
 * </pre>
 *
 * kde jsou parametry
 *
 * terepeut - informace o jméně příjmení terapeuta a jeho praconí době alert je
 * určen pro ošetření chyb - mohou se tím nahradit vystavování výjimek logger je
 * učen pro monitorování vkládání a odebírání termíů ze seznamu.
 *
 * @author karel@simerda.cz
 */
public interface Sprava extends Iterable<Termin> {

    /**
     * Metoda vloží termin s terapií do seznamu na příslušnou pozici podle data
     * začátku termínu.
     * <p>
     * To znamená, že seznam termínu bude setříden podle data a času začátku
     * termínu
     * <p>
     * Operace vkládání bude logována přes Consumer<String> logger;.
     *
     * @param termin vkládaný termín s terapií
     *
     * @throws SpravceException výjimka se vystaví v případě, že nebylo možné
     * vložení dokončit
     */
    void vlozTermin(Termin termin) throws SpravceException;

    /**
     * Metoda vloží termin s terapií na požadovanou pozici seznamu. Ale před tím
     * se metoda přesvědčí, zda je možné termín vložit a to pomocí metody
     * jeVolno.
     * <p>
     * Operace vkládní bude logována přes Consumer<String> logger;.
     *
     * @param termin vkládaný termín s terapií
     * @param pozice zpristupnění termínu
     *
     * @throws SpravceException
     */
    void vlozTermin(Termin termin, Pozice pozice) throws SpravceException;

    /**
     * Metoda zpřístupní termín z požadované pozice (první, poslední,
     * předchůdce, následník, aktuální) a posune aktuální ukazatel v seznamu
     * termínů.
     *
     * @param pozice zpristupnění termínu
     *
     * @return odkaz na zpřístupněný termín
     *
     * @throws sprava.SpravceException
     */
    Termin zpristupniTermin(Pozice pozice) throws SpravceException;

    /**
     * Metoda odebere termín z požadované pozice (první, poslední, předchůdce,
     * následník, aktuální). Pokud se odebere aktuální termín, bude automaticky
     * po odebrání termínu nastaven první termín jako aktuální.
     * <p>
     * Operace odebrání bude logována přes Consumer<String> logger;.
     *
     * @param pozice
     *
     * @return když je vseznamu termín, tak ho vratí, jinak vystaví výjimku
     *
     * @throws SpravceException
     */
    Termin odeberTermin(Pozice pozice) throws SpravceException;

    /**
     * Metoda ověří, zda je místo pro termín volné.
     *
     * @param odKdy datum a čas začátku termínu terapie
     * @param doKdy datum a čas konce termínu terapie
     *
     * @return vrací true, když je místo volné, jinak false
     * @throws sprava.SpravceException
     */
    boolean jeVolno(LocalDateTime odKdy, LocalDateTime doKdy) throws SpravceException;

    /**
     * Metoda dodá matici obsazenosti pracovní doby za dané obdobi
     *
     * @param odKdy datum od kterého se bude zjišťovat
     * @param doKdy datum do kterého se bude zjišťovat
     *
     * @return
     */
    MaticeObsazenosti getObsazenost(LocalDate odKdy, LocalDate doKdy);

    /**
     * Metoda najde vplné časové okno požadované délky a vytvoří termín. Termín
     * poté lze vložit do seznamu termínů.
     *
     * @param terapie druh terapie
     * @param trvani doba trvání terapie
     * @param odkdy datum od kterého se bude zjišťovat
     * @param dokdy datum do kterého se bude zjišťovat
     *
     * @return vrací odkaz na nový termín, který lze vložit do seznamu termínů.
     * Pokud není volno v daném období, tak se vrací null
     */
    Termin najdiPrvniVolnyTermin(
            Terapie terapie,
            TrvaniTerapie trvani,
            LocalDate odkdy,
            LocalDate dokdy);

    /**
     * Metoda provede serializaci jednotlivých termínů s terapií do souboru.
     *
     * @param soubor adresa a jmého souboru pro uložení termínů
     *
     * @throws sprava.SpravceException
     */
    void uloz(String soubor) throws SpravceException;

    /**
     * Metoda vynuluje seznam termíů a poté seznam naplní uloženými termíny v
     * souboru.
     *
     * @param soubor adresa a jmého souboru se senamem uložených termínů
     *
     * @throws sprava.SpravceException
     */
    void nacti(String soubor) throws SpravceException;

    /**
     * Metoda dodá odkaz na infromace o terapeutovi a jeho pracovní době
     *
     * @return
     */
    Terapeut getTerapeut();

    /**
     * Metoda zruší celý seznam s termíny terapiemi.
     */
    void zrus();

    /* =======================================================================*/
    /**
     * Volitelná metoda k implementaci, která najde další volný termín.
     *
     * @param odkdy
     * @param dokdy
     * @return
     * @throws SpravceException
     */
    default Termin najdiDalsiVolnyTermin(LocalDate odkdy, LocalDate dokdy) throws SpravceException {
        throw new SpravceException("Metoda neni implementována.");
    }

    /**
     * Metoda generuje seznam terapii v náhodných časech a v délkách trvání.
     *
     * @param obdobi
     * @param pocetTerapii
     */
    default void generuj(Obdobi obdobi, int pocetTerapii) {

    }

    /**
     *
     * @return
     */
    default Stream<Termin> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /* =======================================================================*/
 /*
      Různé varianty volání metod s různými typy parametrů.
     */
    /**
     * Metoda vloží sérii termínů do seznamu a seřadí je podle začátků.
     *
     * @param terminy seznam termínů
     */
    default void vlozTermíny(Termin... terminy) {
        java.util.Arrays.stream(terminy).forEach(t -> {
            try {
                vlozTermin(t);
            } catch (SpravceException ex) {
                Logger.getLogger(Sprava.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Metoda dodá matici obsazenosti termínů daném období.
     *
     * @param obdobi období pro sestavení matice obsazenosti.
     *
     * @return vrací matici obsazenosti
     */
    default MaticeObsazenosti getObsazenost(Obdobi obdobi) {
        return getObsazenost(obdobi.getDatumOdKdy(), obdobi.getDatumDoKdy());
    }

    /**
     * Metoda zjistí, zda daný časový úsek je volný.
     *
     * @param odKdy datum a čas od kdy bude volno
     * @param doKdy datum a čas do kdy bude volno
     *
     * @return vraci true. když je časový úsek volný
     *
     * @throws SpravceException
     */
    default boolean jeVolno(String odKdy, String doKdy) throws SpravceException {
        return jeVolno(
                DateTimeUtil.convert(odKdy),
                DateTimeUtil.convert(doKdy));
    }

    /**
     * Metoda najde volný termín.
     *
     *
     * @param terapie
     * @param trvani
     * @param odkdy
     * @param dokdy
     * @return
     */
    default Termin najdiPrvniVolnyTermin(
            Terapie terapie,
            TrvaniTerapie trvani,
            String odkdy,
            String dokdy) {
        return najdiPrvniVolnyTermin(
                terapie,
                trvani,
                DateTimeUtil.convert(odkdy).toLocalDate(),
                DateTimeUtil.convert(dokdy).toLocalDate());
    }

    /**
     * Metoda najde volný termín.
     *
     * @param terapie
     * @param trvani
     * @param obdobi
     * @return
     */
    default Termin najdiPrvniVolnyTermin(
            Terapie terapie,
            TrvaniTerapie trvani,
            Obdobi obdobi) {
        return najdiPrvniVolnyTermin(
                terapie,
                trvani,
                obdobi.getDatumOdKdy(),
                obdobi.getDatumDoKdy());
    }
}
