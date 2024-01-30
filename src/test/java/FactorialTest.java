package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class FactorialTest{
    @Test
    void testFactorialOfZero() {
        assertEquals(1, Factorial.calculateFactorial(0));
    }
    @Test
    void testFactorialOfOne() {
        assertEquals(1, Factorial.calculateFactorial(1));
    }
    @Test
    void testFactorialOfPositiveNumber() {
        assertEquals(120, Factorial.calculateFactorial(5));
    }
    @Test
    void testFactorialWithNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () ->
                Factorial.calculateFactorial(-5));
    }
}
