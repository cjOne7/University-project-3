package collection;

public final class CollectionException extends RuntimeException {

    public CollectionException() {
        super("Collection Exception");
    }

    public CollectionException(final String msg) {
        super("Collection Exception: " + msg);
    }
}
