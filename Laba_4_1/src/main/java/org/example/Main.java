package org.example;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя первого файла: ");
        String fileA = scanner.nextLine();

        System.out.print("Введите имя второго файла: ");
        String fileB = scanner.nextLine();

        System.out.print("Введите имя выходного файла: ");
        String outputFile = scanner.nextLine();

        try {
            List<Student> setA = File.read(fileA);
            List<Student> setB = File.read(fileB);

            while (true) {
                System.out.println("\nВыберите операцию: 1 - Объединение, 2 - Пересечение, 3 - Разность, 0 - Выход");
                System.out.print("Ваш выбор: ");
                String choice = scanner.nextLine();

                List<Student> result = new ArrayList<>();

                switch (choice) {
                    case "1":
                        Set<Student> union = new LinkedHashSet<>(setA);
                        union.addAll(setB);
                        result.addAll(union);
                        break;
                    case "2":
                        for (Student s : setA) {
                            if (setB.contains(s)) result.add(s);
                        }
                        break;
                    case "3":
                        for (Student s : setA) {
                            if (!setB.contains(s)) result.add(s);
                        }
                        break;
                    case "0":
                        System.out.println("Выход из программы.");
                        return;
                    default:
                        System.out.println("Неверный выбор.");
                        continue;
                }

                File.write(outputFile, result);
                System.out.println("Результат записан в файл: " + outputFile);
                System.out.println("Содержимое результата:");
                for (Student s : result) {
                    System.out.println(s);
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
        }
    }
}