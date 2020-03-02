package util;

import therapist_data.WorkHours;
import java.time.LocalDateTime;
import therapy.Term;

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

    Term[][] getMaticeTermin();

    Obdobi getObdobi();

    int getPocetDnu();

    int getPocetHodin();

    WorkHours getPracovniDoba();

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
