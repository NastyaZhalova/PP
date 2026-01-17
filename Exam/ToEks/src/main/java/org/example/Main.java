package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (FileWriter log = new FileWriter("log.txt", true);
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                log.write("Новая игра\n");
                playOneGame(log, scanner);
                System.out.print("Хотите сыграть ещё раз? (1 — да, 0 — нет): ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Введите 1 или 0.");
                    scanner.next();
                }
                int choice = scanner.nextInt();
                if (choice != 1) {
                    System.out.println("Спасибо за игру!");
                    break;
                }
                System.out.println("\nНачинаем новую игру!\n");
            }
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }

    public static void playOneGame(FileWriter log, Scanner scanner) throws IOException {
        Random random = new Random();
        int secret = random.nextInt(100) + 1;
        int attempts = 0;
        System.out.println("Компьютер загадал число от 1 до 100. Попробуйте угадать!");
        while (true) {
            System.out.print("Введите число: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: нужно ввести целое число!");
                scanner.next();
                continue;
            }
            int guess = scanner.nextInt();
            if (guess < 1 || guess > 100) {
                System.out.println("Число должно быть в диапазоне от 1 до 100.");
                continue;
            }
            attempts++;
            log.write("Попытка " + attempts + ": введено " + guess + "\n");
            if (guess == secret) {
                System.out.println("Поздравляю! Вы угадали число за " + attempts + " попыток.");
                log.write("Угадано за " + attempts + " попыток\n\n");
                break;
            } else if (guess < secret) {
                System.out.println("Загаданное число больше.");
            } else {
                System.out.println("Загаданное число меньше.");
            }
        }
    }
}
