package org.example;
public class Factorial {
    public static long calculateFactorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Факториал определен только для неотрицательных чисел");
        if (n == 0 || n == 1) return 1;
        return n * calculateFactorial(n - 1);
    }
}