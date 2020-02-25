package kolekce;

/**
 *
 * @author karel@simerda.cz
 */
public class KolekceException extends RuntimeException {

    public KolekceException() {
        super("Kolekce Exception");
    }

    public KolekceException(String msg) {
        super("Kolekce Exception: " + msg);
    }

}
