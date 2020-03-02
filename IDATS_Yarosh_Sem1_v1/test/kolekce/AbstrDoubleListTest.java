package kolekce;

import colection.KolekceException;
import colection.AbstrDoubleList;
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
        instance.vlozPrvni(new Object());
        instance.vlozPrvni(new Object());
        instance.zrus();
        assertEquals(true, instance.jePrazdny());
    }

    @Test
    public void testZrus2() {
        System.out.println("zrus2");
        AbstrDoubleList instance = new AbstrDoubleList();
        boolean expexted = instance.jePrazdny();
        assertEquals(expexted, true);
    }

    @Test
    public void testJePrazdny1() {
        System.out.println("jePrazdny1");
        AbstrDoubleList instance = new AbstrDoubleList();
        boolean expResult = true;
        boolean result = instance.jePrazdny();
        assertEquals(expResult, result);
    }

    @Test
    public void testJePrazdny2() {
        System.out.println("jePrazdny2");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(new Object());
        boolean expResult = false;
        boolean result = instance.jePrazdny();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMohutnost1() {
        System.out.println("getMohutnost1");
        AbstrDoubleList instance = new AbstrDoubleList();
        int expResult = 0;
        int result = instance.getMohutnost();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMohutnost2() {
        System.out.println("getMohutnost2");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(new Object());
        instance.vlozPosledni(new Object());
        int expResult = 2;
        int result = instance.getMohutnost();
        assertEquals(expResult, result);
    }

    @Test
    public void testVlozPrvni1() {
        System.out.println("vlozPrvni1");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(data);
        instance.vlozPrvni(data2);
        assertEquals(data2, instance.zpristupniPrvni());
    }

    @Test(expected = NullPointerException.class)
    public void testVlozPrvni2() {
        System.out.println("vlozPrvni2");
        Object data = null;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(data);
        fail();
    }

    @Test
    public void testVlozPosledni1() {
        System.out.println("vlozPosledni1");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPosledni(data);
        instance.vlozPosledni(data2);
        assertEquals(data2, instance.zpristupniPosledni());
    }

    @Test(expected = NullPointerException.class)
    public void testVlozPosledni2() {
        System.out.println("vlozPosledni2");
        Object data = null;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPosledni(data);
        fail();
    }

    @Test(expected = NullPointerException.class)
    public void testVlozNaslednika1() {
        System.out.println("vlozNaslednika1");
        Object data = null;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozNaslednika(data);
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testVlozNaslednika3() {
        System.out.println("vlozNaslednika3");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(data);
        instance.vlozPrvni(data2);
        instance.vlozNaslednika(data);
        fail();
    }

    @Test
    public void testVlozNaslednika4() {
        System.out.println("vlozNaslednika4");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        instance.vlozNaslednika(data);
        assertEquals(instance.zpristupniNaslednika(), data);
    }

    @Test(expected = NullPointerException.class)
    public void testVlozPredchudce1() {
        System.out.println("vlozPredchudce1");
        Object data = null;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPredchudce(data);
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testVlozPredchudce3() {
        System.out.println("vlozPredchudce3");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(data);
        instance.vlozPrvni(data2);
        instance.vlozPredchudce(data);
        fail();
    }

    @Test
    public void testVlozPredchudce4() {
        System.out.println("vlozPredchudce4");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        instance.vlozPredchudce(data);
        assertEquals(instance.zpristupniPredchudce(), data);
    }

    @Test(expected = NoSuchElementException.class)
    public void testZpristupniAktualni1() {
        System.out.println("zpristupniAktualni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.zpristupniAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testZpristupniAktualni2() {
        System.out.println("zpristupniAktualni2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPosledni(data);
        instance.vlozPosledni(data2);
        instance.zpristupniAktualni();
        fail();
    }

    @Test
    public void testZpristupniAktualni3() {
        System.out.println("zpristupniAktualni3");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPosledni(data);
        instance.vlozPosledni(data2);
        instance.zpristupniPrvni();
        assertEquals(data, instance.zpristupniAktualni());
    }

    @Test
    public void testZpristupniAktualni4() {
        System.out.println("zpristupniAktualni4");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPosledni(data);
        instance.vlozPosledni(data2);
        instance.zpristupniPosledni();
        assertEquals(data2, instance.zpristupniAktualni());
    }

    @Test(expected = NoSuchElementException.class)
    public void testZpristupniPrvni1() {
        System.out.println("zpristupniPrvni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.zpristupniPrvni();
        fail();
    }

    @Test
    public void testZpristupniPrvni2() {
        System.out.println("zpristupniPrvni2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(data);
        instance.vlozPrvni(data2);
        assertEquals(data2, instance.zpristupniPrvni());
    }

    @Test(expected = NoSuchElementException.class)
    public void testZpristupniPosledni1() {
        System.out.println("zpristupniPosledni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.zpristupniPosledni();
        fail();
    }

    @Test
    public void testZpristupniPosledni2() {
        System.out.println("zpristupniPosledni2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPosledni(data);
        instance.vlozPosledni(data2);
        assertEquals(data2, instance.zpristupniPosledni());
    }

    @Test(expected = KolekceException.class)
    public void testZpristupniNaslednika1() {
        System.out.println("zpristupniNaslednika1");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(data);
        instance.vlozPrvni(data2);
        instance.zpristupniNaslednika();
        fail();
    }

    @Test
    public void testZpristupniNaslednika3() {
        System.out.println("zpristupniNaslednika3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        instance.zpristupniNaslednika();
        assertEquals(data[2], instance.zpristupniNaslednika());

    }

    @Test(expected = KolekceException.class)
    public void testZpristupniPredchudce2() {
        System.out.println("zpristupniPredchudce2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPrvni(data);
        instance.vlozPrvni(data2);
        instance.zpristupniPredchudce();
        fail();
    }

    @Test
    public void testZpristupniPredchudce3() {
        System.out.println("zpristupniPredchudce3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPosledni();
        instance.zpristupniPredchudce();
        assertEquals(data[2], instance.zpristupniPredchudce());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberAktualni1() {
        System.out.println("odeberAktualni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.odeberAktualni();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testOdeberAktualni2() {
        System.out.println("odeberAktualni2");
        Integer data = 10;
        Integer data2 = 20;
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.vlozPosledni(data);
        instance.vlozPosledni(data2);
        instance.odeberAktualni();
        fail();
    }

    @Test
    public void testOdeberAktualni3() {
        System.out.println("odeberAktualni3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        assertEquals(data[0], instance.odeberAktualni());
        assertEquals(data[1], instance.zpristupniPrvni());
    }

    @Test
    public void testOdeberAktualni4() {
        System.out.println("odeberAktualni3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPosledni();
        instance.odeberAktualni();
        assertEquals(data[0], instance.zpristupniAktualni());
    }

    @Test(expected = KolekceException.class)
    public void testOdeberPrvni1() {
        System.out.println("odeberPrvni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.odeberPrvni();
        fail();
    }

    @Test
    public void testOdeberPrvni2() {
        System.out.println("odeberPrvni2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        assertEquals(data[0], instance.odeberPrvni());
        assertEquals(data[1], instance.zpristupniAktualni());
    }

    @Test(expected = KolekceException.class)
    public void testOdeberPosledni1() {
        System.out.println("odeberPosledni1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.odeberPosledni();
        fail();
    }

    @Test
    public void testOdeberPosledni2() {
        System.out.println("odeberPosledni2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPosledni();
        assertEquals(data[4], instance.odeberPosledni());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberNaslednika1() {
        System.out.println("odeberNaslednika1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.odeberNaslednika();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testOdeberNaslednika2() {
        System.out.println("odeberNaslednika2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.odeberNaslednika();
        fail();
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberNaslednika3() {
        System.out.println("odeberNaslednika3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPosledni();
        instance.odeberNaslednika();
        fail();
    }

    @Test
    public void testOdeberNaslednika4() {
        System.out.println("odeberNaslednika4");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        assertEquals(data[1], instance.odeberNaslednika());
    }

    @Test
    public void testOdeberNaslednika5() {
        System.out.println("odeberNaslednika5");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        instance.odeberNaslednika();
        instance.odeberNaslednika();
        instance.odeberNaslednika();
        instance.odeberNaslednika();
        assertEquals(data[0], instance.zpristupniAktualni());
    }
    @Test
    public void testOdeberNaslednika6() {
        System.out.println("odeberNaslednika6");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        instance.zpristupniNaslednika();
        instance.zpristupniNaslednika();
        instance.odeberNaslednika();
        instance.odeberPredchudce();
        instance.odeberNaslednika();
        instance.odeberPredchudce();
        assertEquals(data[2], instance.zpristupniAktualni());
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberPredchudce1() {
        System.out.println("odeberPredchudce1");
        AbstrDoubleList instance = new AbstrDoubleList();
        instance.odeberPredchudce();
        fail();
    }

    @Test(expected = KolekceException.class)
    public void testOdeberPredchudce2() {
        System.out.println("odeberPredchudce2");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.odeberPredchudce();
        fail();
    }

    @Test(expected = NoSuchElementException.class)
    public void testOdeberPredchudce3() {
        System.out.println("odeberPredchudce3");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPrvni();
        instance.odeberPredchudce();
        fail();
    }

    @Test
    public void testOdeberPredchudce4() {
        System.out.println("odeberPredchudce4");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPosledni();
        assertEquals(data[3], instance.odeberPredchudce());
    }

    @Test
    public void testOdeberPredchudce5() {
        System.out.println("odeberPredchudce5");
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        instance.zpristupniPosledni();
        instance.odeberPredchudce();
        instance.odeberPredchudce();
        instance.odeberPredchudce();
        instance.odeberPredchudce();
        assertEquals(data[4], instance.zpristupniAktualni());
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
            instance.vlozPosledni(data1);
        }
        Iterator result = instance.iterator();
        int i = 0;
        while (result.hasNext()) {
            assertEquals(data[i++], result.next());
        }
    }

    @Test
    public void testIterator3() {
        System.out.println("iterator3");
        Integer[] expectedData = new Integer[]{10, 20, 30, 40};
        AbstrDoubleList instance = new AbstrDoubleList();
        for (Integer data1 : data) {
            instance.vlozPosledni(data1);
        }
        Iterator result = instance.iterator();
        while (result.hasNext()) {
            result.next();
        }
        result.remove();
        result = instance.iterator();
        int i = 0;
        while (result.hasNext()) {
            assertEquals(result.next(), expectedData[i++]);
        }
        assertEquals(4, instance.getMohutnost());
    }
}
