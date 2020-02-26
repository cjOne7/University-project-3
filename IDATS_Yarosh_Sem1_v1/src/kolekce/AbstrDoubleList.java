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
        if (head == tail) {
            T element = head.element;
            zrus();
            return element;
        }
        if (actual == head) {
            return odeberPrvni();
        }
        if (actual == tail) {
            return odeberPosledni();
        } else {
            T element = actual.element;
            actual.prev.next = actual.next;
            actual.next.prev = actual.prev;
            actual = head;
            size--;
            return element;
        }
    }

    @Override
    public T odeberPrvni() throws KolekceException {
        isEmptyListKE("Empty list");
        if (head == actual) {
            if (head.next != null) {
                actual = head.next;
            }
        }
        if (head == tail) {
            T element = head.element;
            zrus();
            return element;
        } else {
            T element = head.element;
            head = head.next;
            head.prev = null;
            size--;
            return element;
        }
    }

    @Override
    public T odeberPosledni() throws KolekceException {
        isEmptyListKE("Empty list");
        if (tail == actual) {
            actual = head;
        }
        if (head == tail) {
            T element = head.element;
            zrus();
            return element;
        } else {
            T element = tail.element;
            tail = tail.prev;
            tail.next = null;
            size--;
            return element;
        }
    }

    @Override
    public T odeberNaslednika() throws KolekceException, NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        isActualNull("Actual element is null");
        if (head == tail) {
            T element = head.element;
            zrus();
            return element;
        }
        if (actual.next == null) {
            throw new NoSuchElementException("Next element is null");
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
    }

    @Override
    public T odeberPredchudce() throws KolekceException, NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        isActualNull("Actual element is null");
        if (head == tail) {
            T element = head.element;
            zrus();
            return element;
        }
        if (actual.prev == null) {
            throw new NoSuchElementException("Previous element is null");
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
                currentNode = currentNode.next;
                return element;
            }

            @Override
            public void remove() {
                Iterator.super.remove(); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    private void isEmptyListNPE(final String message) {
        if (jePrazdny()) {
            throw new NullPointerException(message);
        }
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
