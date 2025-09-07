package org.example;

import java.util.Scanner;

public class Main {

    public static double Log(int k, double x) {
        if (k <= 0) {
            throw new IllegalArgumentException("k должно быть натуральным числом (k > 0)");
        }
        if (x < -1 || x >= 1) {
            throw new IllegalArgumentException("x должно быть в диапазоне [-1; 1)");
        }

        double e = Math.pow(10, -k);
        double temp = x;
        double sum = -x;
        double n = 2;

        while (Math.abs(temp / n) >= e) {
            temp *= x;
            sum -= temp / n;
            n += 1;
        }

        return sum;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Введите k и x");

        try {
            int k = s.nextInt();
            double x = s.nextDouble();

            double sum = Log(k, x);
            double res = Math.log(1 - x);

            if (Math.abs(sum) < 0.0005) {
                System.out.printf("Результат суммы: %.3f%n", 0.0);
            } else {
                System.out.printf("Результат суммы: %.3f%n", sum);
            }

            System.out.printf("Результат log: %.3f%n", res);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка ввода. Убедитесь, что вы вводите целое k и вещественное x.");
        }
    }
}
