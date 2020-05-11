package kolekce;

import collection.CollectionException;
import collection.AbstrDoubleList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AbstrDoubleListTest {

    private Integer[] data = new Integer[]{10, 20, 30, 40, 50};

    public AbstrDoubleListTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testZrus1() {
        System.out.println("zrus1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(new Object());
        instance.addFirst(new Object());
        instance.clear();
        assertEquals(true, instance.isEmpty());
    }

    @Test
    public void testZrus2() {
        System.out.println("zrus2");
        AbstrDoubleList instance = new AbstrDoubleList();
        boolean expexted = instance.isEmpty();
        assertEquals(expexted, true);
    }

    @Test
    public void testJePrazdny1() {
        System.out.println("jePrazdny1");
        AbstrDoubleList instance = new AbstrDoubleList();
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    @Test
    public void testJePrazdny2() {
        System.out.println("jePrazdny2");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(new Object());
        boolean expResult = false;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMohutnost1() {
        System.out.println("getMohutnost1");
        AbstrDoubleList instance = new AbstrDoubleList();
        int expResult = 0;
        int result = instance.getSize();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMohutnost2() {
        System.out.println("getMohutnost2");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(new Object());
        instance.addLast(new Object());
        int expResult = 2;
        int result = instance.getSize();
        assertEquals(expResult, result);
    }

    @Test
    public void testVlozPrvni1() {
        System.out.println("vlozPrvni1");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(data);
        instance.addFirst(data2);
        assertEquals(data2, instance.getFirst());
    }

    @Test(expected = NullPointerException.class)
    public void testVlozPrvni2() {
        System.out.println("vlozPrvni2");
        Object data = null;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(data);
        fail();
    }

    @Test
    public void testVlozPosledni1() {
        System.out.println("vlozPosledni1");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addLast(data);
        instance.addLast(data2);
        assertEquals(data2, instance.getLast());
    }

    @Test(expected = NullPointerException.class)
    public void testVlozPosledni2() {
        System.out.println("vlozPosledni2");
        Object data = null;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addLast(data);
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void testVlozNaslednika1() {
        System.out.println("vlozNaslednika1");
        Object data = null;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addAfter(data);
        fail();
    }

    @Test(expected = CollectionException.class)
    public void testVlozNaslednika3() {
        System.out.println("vlozNaslednika3");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(data);
        instance.addFirst(data2);
        instance.addAfter(data);
        fail();
    }

    @Test
    public void testVlozNaslednika4() {
        System.out.println("vlozNaslednika4");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getFirst();
        instance.addAfter(data);
        assertEquals(instance.getNext(), data);
    }

    @Test(expected = NullPointerException.class)
    public void testVlozPredchudce1() {
        System.out.println("vlozPredchudce1");
        Object data = null;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addBefore(data);
        fail();
    }

    @Test(expected = CollectionException.class)
    public void testVlozPredchudce3() {
        System.out.println("vlozPredchudce3");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(data);
        instance.addFirst(data2);
        instance.addBefore(data);
        fail();
    }

    @Test
    public void testVlozPredchudce4() {
        System.out.println("vlozPredchudce4");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getFirst();
        instance.addBefore(data);
        assertEquals(instance.getPrevious(), data);
    }

    @Test(expected = NoSuchElementException.class)
    public void testZpristupniAktualni1() {
        System.out.println("zpristupniAktualni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.getActual();
        fail();
    }

    @Test(expected = CollectionException.class)
    public void testZpristupniAktualni2() {
        System.out.println("zpristupniAktualni2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addLast(data);
        instance.addLast(data2);
        instance.getActual();
        fail();
    }

    @Test
    public void testZpristupniAktualni3() {
        System.out.println("zpristupniAktualni3");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addLast(data);
        instance.addLast(data2);
        instance.getFirst();
        assertEquals(data, instance.getActual());
    }

    @Test
    public void testZpristupniAktualni4() {
        System.out.println("zpristupniAktualni4");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addLast(data);
        instance.addLast(data2);
        instance.getLast();
        assertEquals(data2, instance.getActual());
    }

    @Test(expected = NoSuchElementException.class)
    public void testZpristupniPrvni1() {
        System.out.println("zpristupniPrvni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.getFirst();
        fail();
    }

    @Test
    public void testZpristupniPrvni2() {
        System.out.println("zpristupniPrvni2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(data);
        instance.addFirst(data2);
        assertEquals(data2, instance.getFirst());
    }

    @Test(expected = NoSuchElementException.class)
    public void testZpristupniPosledni1() {
        System.out.println("zpristupniPosledni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.getLast();
        fail();
    }

    @Test
    public void testZpristupniPosledni2() {
        System.out.println("zpristupniPosledni2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addLast(data);
        instance.addLast(data2);
        assertEquals(data2, instance.getLast());
    }

    @Test(expected = CollectionException.class)
    public void testZpristupniNaslednika1() {
        System.out.println("zpristupniNaslednika1");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(data);
        instance.addFirst(data2);
        instance.getNext();
        fail();
    }

    @Test
    public void testZpristupniNaslednika3() {
        System.out.println("zpristupniNaslednika3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getFirst();
        instance.getNext();
        assertEquals(data[2], instance.getNext());

    }

    @Test(expected = CollectionException.class)
    public void testZpristupniPredchudce2() {
        System.out.println("zpristupniPredchudce2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addFirst(data);
        instance.addFirst(data2);
        instance.getPrevious();
        fail();
    }

    @Test
    public void testZpristupniPredchudce3() {
        System.out.println("zpristupniPredchudce3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getLast();
        instance.getPrevious();
        assertEquals(data[2], instance.getPrevious());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberAktualni1() {
        System.out.println("odeberAktualni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.deleteActual();
        fail();
    }

    @Test(expected = CollectionException.class)
    public void testOdeberAktualni2() {
        System.out.println("odeberAktualni2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addLast(data);
        instance.addLast(data2);
        instance.deleteActual();
        fail();
    }

    @Test
    public void testOdeberAktualni3() {
        System.out.println("odeberAktualni3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getFirst();
        assertEquals(data[0], instance.deleteActual());
        assertEquals(data[1], instance.getFirst());
    }

    @Test
    public void testOdeberAktualni4() {
        System.out.println("odeberAktualni3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getLast();
        instance.deleteActual();
        assertEquals(data[0], instance.getActual());
    }

    @Test(expected = CollectionException.class)
    public void testOdeberPrvni1() {
        System.out.println("odeberPrvni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.deleteFirst();
        fail();
    }

    @Test
    public void testOdeberPrvni2() {
        System.out.println("odeberPrvni2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getFirst();
        assertEquals(data[0], instance.deleteFirst());
        assertEquals(data[1], instance.getActual());
    }

    @Test(expected = CollectionException.class)
    public void testOdeberPosledni1() {
        System.out.println("odeberPosledni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.deleteLast();
        fail();
    }

    @Test
    public void testOdeberPosledni2() {
        System.out.println("odeberPosledni2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getLast();
        assertEquals(data[4], instance.deleteLast());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberNaslednika1() {
        System.out.println("odeberNaslednika1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.deleteNext();
        fail();
    }

    @Test(expected = CollectionException.class)
    public void testOdeberNaslednika2() {
        System.out.println("odeberNaslednika2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.deleteNext();
        fail();
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberNaslednika3() {
        System.out.println("odeberNaslednika3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getLast();
        instance.deleteNext();
        fail();
    }

    @Test
    public void testOdeberNaslednika4() {
        System.out.println("odeberNaslednika4");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getFirst();
        assertEquals(data[1], instance.deleteNext());
    }

    @Test
    public void testOdeberNaslednika5() {
        System.out.println("odeberNaslednika5");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getFirst();
        instance.deleteNext();
        instance.deleteNext();
        instance.deleteNext();
        instance.deleteNext();
        assertEquals(data[0], instance.getActual());
    }

    @Test
    public void testOdeberNaslednika6() {
        System.out.println("odeberNaslednika6");
        AbstrDoubleList<Integer> instance = new AbstrDoubleList<>();
        instance.addFirst(10);
        instance.getLast();
        instance.addAfter(20);
        instance.addBefore(30);
        instance.addAfter(40);
        instance.addBefore(50);
        instance.getFirst();
        int[] expResult = {50, 10, 40, 20};
        int[] result = {
            instance.deleteNext(),
            instance.deleteNext(),
            instance.deleteNext(),
            instance.deleteNext()
        };
        assertArrayEquals(expResult, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberNaslednika7() {
        System.out.println("odeberNaslednika7");
        AbstrDoubleList<Integer> instance = new AbstrDoubleList<>();
        instance.addFirst(10);
        instance.deleteNext();
        fail();
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberPredchudce1() {
        System.out.println("odeberPredchudce1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.deletePrevious();
        fail();
    }

    @Test(expected = CollectionException.class)
    public void testOdeberPredchudce2() {
        System.out.println("odeberPredchudce2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.deletePrevious();
        fail();
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberPredchudce3() {
        System.out.println("odeberPredchudce3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getFirst();
        instance.deletePrevious();
        fail();
    }

    @Test
    public void testOdeberPredchudce4() {
        System.out.println("odeberPredchudce4");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getLast();
        assertEquals(data[3], instance.deletePrevious());
    }

    @Test
    public void testOdeberPredchudce5() {
        System.out.println("odeberPredchudce5");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        instance.getLast();
        instance.deletePrevious();
        instance.deletePrevious();
        instance.deletePrevious();
        instance.deletePrevious();
        assertEquals(data[4], instance.getActual());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberPredchudce6() {
        System.out.println("odeberPredchudce6");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.addLast(10);
        instance.deletePrevious();
        fail();
    }
    
    @Test
    public void testIterator1() {
        System.out.println("iterator1");
        AbstrDoubleList instance = new AbstrDoubleList();
        Iterator result = instance.iterator();
        assertNotNull(result);
    }

    @Test
    public void testIterator2() {
        System.out.println("iterator2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.addLast(data1);
        }
        Iterator result = instance.iterator();
        int i = 0;
        while (result.hasNext()) {
            assertEquals(data[i++], result.next());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testIterator3() {
        System.out.println("iterator3");
        AbstrDoubleList<Integer> instance = new AbstrDoubleList<>();
        for (Integer data1 : data) {
            instance.addFirst(data1);
        }
        Iterator<Integer> it = instance.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
        }
        it.remove();
        it = instance.iterator();
        for (int i = 0; i < 5; i++) {
            Integer next = it.next();
        }
        fail();
    }
}
