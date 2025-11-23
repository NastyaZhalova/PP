package org.example;

import java.util.*;
import java.io.*;

public class TextJustify {
    private static final String INDENT = "    "; // красная строка (4 пробела)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ширину строки: ");
        int width = scanner.nextInt();

        FileWorker fw = new FileWorker("input.txt", "output.txt");

        try {
            List<String> inputLines = fw.readFile();
            List<List<String>> paragraphs = splitIntoParagraphs(inputLines);

            List<String> justified = new ArrayList<>();
            for (List<String> paragraph : paragraphs) {
                String text = String.join(" ", paragraph);
                text = text.replaceAll("\\s+", " "); // убираем лишние пробелы

                // первая строка абзаца учитывает отступ
                List<String> lines = justifyText(text, width, true);
                justified.addAll(lines);
                justified.add(""); // пустая строка между абзацами
            }

            fw.writeFile(justified);
            System.out.println("Текст успешно выровнен с абзацами и красной строкой");
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлами: " + e.getMessage());
        }
    }

    public static List<List<String>> splitIntoParagraphs(List<String> lines) {
        List<List<String>> paragraphs = new ArrayList<>();
        List<String> current = new ArrayList<>();
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                if (!current.isEmpty()) {
                    paragraphs.add(new ArrayList<>(current));
                    current.clear();
                }
            } else {
                current.add(line);
            }
        }
        if (!current.isEmpty()) {
            paragraphs.add(current);
        }
        return paragraphs;
    }

    public static List<String> justifyText(String text, int width, boolean firstParagraphLine) {
        List<String> result = new ArrayList<>();
        String[] words = text.split(" ");
        List<String> lineWords = new ArrayList<>();
        int currentLength = 0;

        boolean isFirstLine = true;

        for (String word : words) {
            int effectiveWidth = width;
            if (isFirstLine && firstParagraphLine) {
                effectiveWidth -= INDENT.length(); // уменьшаем ширину для первой строки
            }

            if (currentLength + word.length() + lineWords.size() <= effectiveWidth) {
                lineWords.add(word);
                currentLength += word.length();
            } else {
                String line = justifyLine(lineWords, effectiveWidth);
                if (isFirstLine && firstParagraphLine) {
                    line = INDENT + line; // добавляем красную строку
                    isFirstLine = false;
                }
                result.add(line);

                lineWords.clear();
                lineWords.add(word);
                currentLength = word.length();
            }
        }

        if (!lineWords.isEmpty()) {
            String lastLine = String.join(" ", lineWords);
            if (isFirstLine && firstParagraphLine) {
                lastLine = INDENT + lastLine;
            }
            result.add(lastLine);
        }

        return result;
    }

    public static String justifyLine(List<String> words, int width) {
        if (words.size() == 1) {
            return words.get(0) + " ".repeat(width - words.get(0).length());
        }

        int totalChars = words.stream().mapToInt(String::length).sum();
        int spaces = width - totalChars;
        int gaps = words.size() - 1;

        int spacePerGap = spaces / gaps;
        int extraSpaces = spaces % gaps;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.size(); i++) {
            sb.append(words.get(i));
            if (i < gaps) {
                sb.append(" ".repeat(spacePerGap));
                if (extraSpaces > 0) {
                    sb.append(" ");
                    extraSpaces--;
                }
            }
        }
        return sb.toString();
    }
}
