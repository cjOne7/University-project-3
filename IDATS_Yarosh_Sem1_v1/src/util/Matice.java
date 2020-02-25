package util;

import terapie.PracovniDoba;
import java.time.LocalDateTime;
import sprava.SpravceException;
import terapie.Termin;

/**
 *
 * @author kasi0004
 */
public interface Matice {

    /**
     *
     * @param den
     * @param hodina
     *
     * @return
     */
    LocalDateTime getDatumACas(int den, int hodina);

    boolean[][] getMatice();

    LocalDateTime najdiPrvniVolneMisto(int delka);

    char[][] getMaticeChar(char znakVolno, char znakObsazeno);

    Termin[][] getMaticeTermin();

    Obdobi getObdobi();

    int getPocetDnu();

    int getPocetHodin();

    PracovniDoba getPracovniDoba();

    /**
     *
     * @param den den od začátku období
     * @param hodina cas v pracovní době v hodinách
     *
     * @return
     */
    boolean jeVolno(int den, int hodina);

    boolean jeVolno(LocalDateTime odKdy, LocalDateTime doKdy);

    void setObsazeni(LocalDateTime date, int delka);

}
