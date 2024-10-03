package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {

    @Test
    public void dequeTest() {
        StudentArrayDeque<Integer> L = new StudentArrayDeque<>();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            int select = StdRandom.uniform(5);
            if (select == 0) {
                L.addFirst();
            }
        }
    }
}
