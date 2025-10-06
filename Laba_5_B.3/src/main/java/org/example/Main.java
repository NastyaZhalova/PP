package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> inputWords = new ArrayList<>();

        System.out.println("Введите строки текста (пустая строка завершает ввод):");

        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;

            String[] words = line.split("[\\s,.]+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    inputWords.add(word.toLowerCase());
                }
            }
        }

        Huffman compressor = new Huffman();
        Map<String, String> codes = compressor.buildHuffmanCodes(inputWords);
        List<String> compressed = compressor.compress(inputWords, codes);

        System.out.println("\nКоды Хаффмана:");
        codes.forEach((w, c) -> System.out.println(w + " → " + c));

        System.out.println("\nСжатый текст:");
        System.out.println(String.join(" ", compressed));
    }
}
