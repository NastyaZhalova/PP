package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    public static double roundToThree(double value) {
        return new BigDecimal(value)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Введите k и x");

        try {
            int k = s.nextInt();
            double x = s.nextDouble();

            double sum = roundToThree(LogCalculator.calculate(k, x));
            double res = roundToThree(Math.log(1 - x));

            System.out.println("Результат суммы: " + sum);

            System.out.println("Результат log: " + res);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка ввода. Убедитесь, что вы вводите целое k и вещественное x.");
        }
    }
}
