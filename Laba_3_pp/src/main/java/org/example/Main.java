package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        System.out.println("Введите строки текста (пустая строка завершает ввод):");
        while (true) {
            String line = s.nextLine();
            if (line.isEmpty()) break;
            lines.add(line);
        }

        System.out.print("Введите k: ");
        int k = s.nextInt();

        System.out.println("\nРезультат:");
        for (String line : lines) {
            String[] parts = StringClass.splitLine(line, k);
            System.out.println("Первая часть: '" + parts[0] + "'");
            System.out.println("Вторая часть: '" + parts[1] + "'");
            System.out.println();
        }
    }
}
