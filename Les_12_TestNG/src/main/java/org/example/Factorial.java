package org.example;
public class Factorial {
    public static long Factorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        long result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        return result;
    }
    public static byte[] factorial(int i) {
    }
}