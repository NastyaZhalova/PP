package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringStack stack = new StringStack();

        System.out.print("Сколько строк добавить в стек? ");
        int count = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < count; i++) {
            System.out.print("Введите строку " + i + ": ");
            String text = scanner.nextLine();
            stack.push(text);
        }

        System.out.println("\nВсе строки в стеке:");
        for (int i = 0; i < stack.size(); i++) {
            System.out.println( i + " - " + stack.get(i));
        }

        System.out.print("\nВведите индекс строки для изменения: ");
        int index = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите новую строку: ");
        String newText = scanner.nextLine();

        String previousText = stack.get(index);

        stack.set(index, newText);

        System.out.println("\nИзменение выполнено:");
        System.out.println("Предыдущая строка: " + previousText);
        System.out.println("Новая строка:      " + stack.get(index));

        System.out.println("\nСтек после изменения:");
        for (int i = 0; i < stack.size(); i++) {
            System.out.println( i + " - " + stack.get(i));
        }

        scanner.close();
    }
}
