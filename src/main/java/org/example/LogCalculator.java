package org.example;

public class LogCalculator {

    public static double calculate(int k, double x) {
        validateInput(k, x);

        double epsilon = Math.pow(10, -k);
        double term = x;
        double sum = -x;
        double n = 2;
        int maxIterations = 10000;
        int count = 0;

        while (Math.abs(term / n) >= epsilon && count < maxIterations) {
            term *= x;
            sum -= term / n;
            n++;
            count ++;
        }

        return sum;
    }

    private static void validateInput(int k, double x) {
        if (k <= 0) {
            throw new IllegalArgumentException("k должно быть натуральным числом (k > 0)");
        }
        if (x < -1 || x >= 1) {
            throw new IllegalArgumentException("x должно быть в диапазоне [-1; 1)");
        }
    }
}
