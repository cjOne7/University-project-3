package kolekce;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrDoubleList<T> implements DoubleList<T>, Serializable {

    private Node head;
    private Node tail;
    private Node actual;

    private int size;

    @Override
    public void zrus() {
        for (Node x = head; x != null;) {
            Node nextNode = x.next;
            x.element = null;
            x.next = null;
            x.prev = null;
            x = nextNode;
        }
        head = tail = actual = null;
        size = 0;
    }

    @Override
    public boolean jePrazdny() {
        return size == 0;
    }

    @Override
    public int getMohutnost() {
        return size;
    }

    @Override
    public void vlozPrvni(T data) throws NullPointerException {
        isDataNull(data, "Data is null");
        Node newNode = new Node(data, head, null);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    @Override
    public void vlozPosledni(T data) throws NullPointerException {
        isDataNull(data, "Data is null");
        Node newNode = new Node(data, null, tail);
        if (tail == null) {
            tail = newNode;
            head = tail;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void vlozNaslednika(T data) throws KolekceException, NullPointerException {
        isDataNull(data, "Data is null");
        isActualNull("Actual element is null");
        if (actual == tail) {
            vlozPosledni(data);
        }
        Node nextNode = actual.next;
        Node newNode = new Node(data, nextNode, actual);
        actual.next = newNode;
        if (nextNode == null) {
            tail = newNode;
        } else {
            nextNode.prev = newNode;
        }
        size++;
    }

    @Override
    public void vlozPredchudce(T data) throws KolekceException, NullPointerException {
        isDataNull(data, "Data is null");
        isActualNull("Actual element is null");
        if (actual == head) {
            vlozPrvni(data);
        }
        Node prevNode = actual.prev;
        Node newNode = new Node(data, actual, prevNode);
        actual.prev = newNode;
        if (prevNode == null) {
            head = newNode;
        } else {
            prevNode.next = newNode;
        }
        size++;
    }

    @Override
    public T zpristupniAktualni() throws NoSuchElementException, KolekceException {
        isEmptyListNSEE("Enmpty list");
        isActualNull("Actual element is null");
        return actual.element;
    }

    @Override
    public T zpristupniPrvni() throws NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        actual = head;
        return head.element;
    }

    @Override
    public T zpristupniPosledni() throws NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        actual = tail;
        return tail.element;
    }

    @Override
    public T zpristupniNaslednika() throws NoSuchElementException, KolekceException {
        isActualNull("Actual element is null");
        if (actual.next == null) {
            throw new NoSuchElementException("Next element after actual is null");
        }
        actual = actual.next;
        return actual.element;
    }

    @Override
    public T zpristupniPredchudce() throws NoSuchElementException, KolekceException {
        isActualNull("Actual element is null");
        if (actual.prev == null) {
            throw new NoSuchElementException("Next element after actual is null");
        }
        actual = actual.prev;
        return actual.element;
    }

    @Override
    public T odeberAktualni() throws KolekceException, NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        isActualNull("Actual element is null");
        final T element = actual.element;
        unlink(actual);
        return element;
    }

    @Override
    public T odeberPrvni() throws KolekceException {
        isEmptyListKE("Empty list");
        if (head == actual) {
            if (head.next != null) {
                actual = head.next;
            }
        }
        final T element = head.element;
        Node next = head.next;
        head.element = null;
        head.next = null;
        head = next;
        if (next == null) {
            tail = null;
        } else {
            next.prev = null;
        }
        size--;
        return element;
    }

    @Override
    public T odeberPosledni() throws KolekceException {
        isEmptyListKE("Empty list");
        if (tail == actual) {
            if (tail.prev != null) {
                actual = tail.prev;
            }
        }
        final T element = tail.element;
        Node prev = tail.prev;
        tail.element = null;
        tail.prev = null;
        tail = prev;
        if (prev == null) {
            head = null;
        } else {
            prev.next = null;
        }
        size--;
        return element;
    }

    @Override
    public T odeberNaslednika() throws KolekceException, NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        isActualNull("Actual element is null");
        if (actual.next == null) {
            throw new NoSuchElementException("Next element is null");
        }
        if (head == tail) {
            T element = head.element;
            zrus();
            return element;
        }
        if (actual.next == tail) {
            return odeberPosledni();
        } else {
            T element = actual.next.element;
            actual.next.next.prev = actual;
            actual.next = actual.next.next;
            size--;
            return element;
        }
//        final T element = actual.next.element;
//        if (head.next == tail) {
//            actual.next = null;
//            tail = actual;
//        } else {
//            actual.next.next.prev = actual;
//            actual.next = actual.next.next;
//        }
//        size--;
//        return element;
    }

    @Override
    public T odeberPredchudce() throws KolekceException, NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        isActualNull("Actual element is null");
        if (actual.prev == null) {
            throw new NoSuchElementException("Previous element is null");
        }
        if (head == tail) {
            T element = head.element;
            zrus();
            return element;
        }
        if (actual.prev == head) {
            return odeberPrvni();
        } else {
            T element = actual.prev.element;
            actual.prev.prev.next = actual;
            actual.prev = actual.prev.prev;
            size--;
            return element;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node currentNode = head;
            Node deletedNode = null;

            @Override
            public boolean hasNext() {
                return currentNode != null;
            }

            @Override
            public T next() {
                if (currentNode == null) {
                    throw new NoSuchElementException("The list is ended");
                }
                T element = currentNode.element;
                deletedNode = currentNode;
                currentNode = currentNode.next;
                return element;
            }

            @Override
            public void remove() {
                unlink(deletedNode);
            }
        };
    }

    private void unlink(Node node) {
        Node next = node.next;
        Node prev = node.prev;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
            node.prev = null;
        }
        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            node.next = null;
        }
        node.element = null;
        actual = head;
        size--;
    }

    private void isEmptyListNSEE(final String message) {
        if (jePrazdny()) {
            throw new NoSuchElementException(message);
        }
    }

    private void isEmptyListKE(final String message) {
        if (jePrazdny()) {
            throw new KolekceException(message);
        }
    }

    private void isActualNull(final String message) {
        if (actual == null) {
            throw new KolekceException(message);
        }
    }

    private void isDataNull(final T data, final String message) {
        if (data == null) {
            throw new NullPointerException(message);
        }
    }

    private class Node implements Serializable {

        private T element;
        private Node next;
        private Node prev;

        public Node(T element, Node next, Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
}
