package flik;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import flik.Flik;

import  static org.junit.Assert.*;

public class TestFlik {

    @Test
    public void randomizedTest() {
        int num1 = 150;
        int num2 = 150;
        assertTrue(String.format("num1 = %d", num1), Flik.isSameNumber(num1, num2));
    }
}
