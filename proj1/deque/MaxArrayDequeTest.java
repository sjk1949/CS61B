package deque;

import java.util.Comparator;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    /** A test class has multi-comparator */
    private static class Dog {
        String name;
        int size;

        public Dog(String name, int size) {
            this.name = name;
            this.size = size;
        }

        private static class NameComparator implements Comparator<Dog> {
            @Override
            public int compare(Dog o1, Dog o2) {
                return o1.name.compareTo(o2.name);
            }
        }

        private static class SizeComparator implements  Comparator<Dog> {
            @Override
            public int compare(Dog o1, Dog o2) {
                return o1.size - o2.size;
            }
        }

        public static Comparator<Dog> getNameComparator() {
            return new NameComparator();
        }

        public static Comparator<Dog> getSizeComparator() {
            return new SizeComparator();
        }
    }

    @Test
    public void maxTest() {
        MaxArrayDeque<Dog> L;
        L = new MaxArrayDeque<>(Dog.getNameComparator());
        L.addLast(new Dog("bruce", 12));
        L.addLast(new Dog("ital", 18));
        L.addLast(new Dog("hachei", 20));
        L.addLast(new Dog("alice", 15));
        assertEquals("ital", L.max().name);
        assertEquals("hachei", L.max(Dog.getSizeComparator()).name);
    }

    @Test
    public void allNullTest(){
        MaxArrayDeque<Dog> L;
        L = new MaxArrayDeque<>(Dog.getNameComparator());
        L.addLast(null);
        L.addLast(null);
        L.addLast(null);
        L.addLast(null);
        assertNull(L.max());
        assertNull(L.max(Dog.getSizeComparator()));
    }

    @Test
    public void nullTest(){
        MaxArrayDeque<Dog> L;
        L = new MaxArrayDeque<>(Dog.getNameComparator());
        L.addLast(null);
        L.addLast(null);
        L.addLast(new Dog("bruce", 12));
        L.addLast(null);
        L.addLast(new Dog("jack", 20));
        L.addLast(new Dog("cota", 30));
        assertEquals("jack", L.max().name);
        assertEquals("cota", L.max(Dog.getSizeComparator()).name);
    }
}
