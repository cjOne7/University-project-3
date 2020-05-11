package collection;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import therapy.Term;

public final class AbstrDoubleList<T> implements DoubleList<T>, Serializable {

    private Node head;
    private Node tail;
    private Node actual;

    private int size;

    @Override
    public void clear() {
        head = tail = actual = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void addFirst(T data) throws NullPointerException {
        isDataNull(data, "Data is null");
        final Node newNode = new Node(data, head, null);
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
    public void addLast(T data) throws NullPointerException {
        isDataNull(data, "Data is null");
        final Node newNode = new Node(data, null, tail);
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
    public void addAfter(T data) throws CollectionException, NullPointerException {
        isDataNull(data, "Data is null");
        isActualNull("Actual element is null");
        if (actual == tail) {
            addLast(data);
        } else {
            final Node nextNode = actual.next;
            final Node newNode = new Node(data, nextNode, actual);
            actual.next = newNode;
            if (nextNode == null) {
                tail = newNode;
            } else {
                nextNode.prev = newNode;
            }
            size++;
        }
    }

    @Override
    public void addBefore(T data) throws CollectionException, NullPointerException {
        isDataNull(data, "Data is null");
        isActualNull("Actual element is null");
        if (actual == head) {
            addFirst(data);
        } else {
            final Node prevNode = actual.prev;
            final Node newNode = new Node(data, actual, prevNode);
            actual.prev = newNode;
            if (prevNode == null) {
                head = newNode;
            } else {
                prevNode.next = newNode;
            }
            size++;
        }
    }

    @Override
    public T getActual() throws NoSuchElementException, CollectionException {
        isEmptyListNSEE("Enmpty list");
        isActualNull("Actual element is null");
        return actual.element;
    }

    @Override
    public T getFirst() throws NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        actual = head;
        return head.element;
    }

    @Override
    public T getLast() throws NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        actual = tail;
        return tail.element;
    }

    @Override
    public T getNext() throws NoSuchElementException, CollectionException {
        isActualNull("Actual element is null");
        if (actual.next == null) {
            throw new NoSuchElementException("Next element after actual is null");
        }
        actual = actual.next;
        return actual.element;
    }

    @Override
    public T getPrevious() throws NoSuchElementException, CollectionException {
        isActualNull("Actual element is null");
        if (actual.prev == null) {
            throw new NoSuchElementException("Next element after actual is null");
        }
        actual = actual.prev;
        return actual.element;
    }

    @Override
    public T deleteActual() throws CollectionException, NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        isActualNull("Actual element is null");
        final T element = actual.element;
        unlink(actual);
        return element;
    }

    @Override
    public T deleteFirst() throws CollectionException {
        isEmptyListKE("Empty list");
        if (head == actual) {
            if (head.next != null) {
                actual = head.next;
            }
        }
        final T element = head.element;
        final Node next = head.next;
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
    public T deleteLast() throws CollectionException {
        isEmptyListKE("Empty list");
        if (tail == actual) {
            if (tail.prev != null) {
                actual = tail.prev;
            }
        }
        final T element = tail.element;
        final Node prev = tail.prev;
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
    public T deleteNext() throws CollectionException, NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        if (head.next == null) {
            throw new NoSuchElementException("List contains only one element.");
        }
        isActualNull("Actual element is null");
        if (actual.next == null) {
            throw new NoSuchElementException("Next element is null.");
        }
        if (head == tail) {
            final T element = head.element;
            clear();
            return element;
        }
        if (actual.next == tail) {
            return deleteLast();
        } else {
            final T element = actual.next.element;
            actual.next.next.prev = actual;
            actual.next = actual.next.next;
            size--;
            return element;
        }
    }

    @Override
    public T deletePrevious() throws CollectionException, NoSuchElementException {
        isEmptyListNSEE("Enmpty list");
        if (tail.prev == null) {
            throw new NoSuchElementException("List contains only one element.");
        }
        isActualNull("Actual element is null");
        if (actual.prev == null) {
            throw new NoSuchElementException("Previous element is null");
        }
        if (head == tail) {
            final T element = head.element;
            clear();
            return element;
        }
        if (actual.prev == head) {
            return deleteFirst();
        } else {
            final T element = actual.prev.element;
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
                final T element = currentNode.element;
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
        if (isEmpty()) {
            throw new NoSuchElementException(message);
        }
    }

    private void isEmptyListKE(final String message) {
        if (isEmpty()) {
            throw new CollectionException(message);
        }
    }

    private void isActualNull(final String message) {
        if (actual == null) {
            throw new CollectionException(message);
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

        public Node(final T element, final Node next, final Node prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public void sortCollection(final AbstrDoubleList<Term> abstrDoubleList) {
        final Object[] arrayOfTerms = abstrDoubleList.stream().toArray();
        for (int i = 1; i < arrayOfTerms.length - 1; i++) {
            for (int j = 0; j < arrayOfTerms.length - 1; j++) {
                final Term term = (Term) arrayOfTerms[j];
                final Term term1 = (Term) arrayOfTerms[j + 1];
                final int day1 = term.getStart().getDayOfYear();
                final int day2 = term1.getStart().getDayOfYear();
                if (day1 > day2) {
                    rearrangeElements(arrayOfTerms, j);
                } else if (day1 == day2) {
                    final int hour1 = term.getStart().getHour();
                    final int hour2 = term1.getStart().getHour();
                    if (hour1 > hour2) {
                        rearrangeElements(arrayOfTerms, j);
                    }
                }
            }
        }
        abstrDoubleList.clear();
        for (Object arrayOfTerm : arrayOfTerms) {
            abstrDoubleList.addLast((Term) arrayOfTerm);
        }
    }

    private void rearrangeElements(Object[] arrayOfTerms, int j) {
        final Term temp = (Term) arrayOfTerms[j];
        arrayOfTerms[j] = arrayOfTerms[j + 1];
        arrayOfTerms[j + 1] = temp;
    }
}
