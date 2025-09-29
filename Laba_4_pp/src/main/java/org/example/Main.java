package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите слова через пробел:");
        String input = scanner.nextLine();

        List<String> words = new ArrayList<>(Arrays.asList(input.split("[\\s,\\.]+")));

        List<String> longestChain = TextClass.findLongestChain(words);

        System.out.println("\nМаксимальная цепочка:");
        if (longestChain.isEmpty()) {
            System.out.println("Цепочка не найдена.");
        } else {
            System.out.println(String.join(" ", longestChain));
        }
    }

}