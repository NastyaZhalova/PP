package org.example;

public class StringClass {
    public static String[] splitLine(String line, int k) {
        String[] words = line.split("[\\s,\\.]+");

        StringBuilder cleanLine = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                cleanLine.append(word).append(" ");
            }
        }

        String fullLine = cleanLine.toString().trim();
        String firstPart = "";
        String secondPart = "";

        if (fullLine.length() <= k) {
            firstPart = fullLine;
            while (firstPart.length() < k) {
                firstPart += " ";
            }
            secondPart = "";
        } else {
            int splitIndex = k;
            while (splitIndex > 0 && !Character.isWhitespace(fullLine.charAt(splitIndex - 1))) {
                splitIndex--;
            }

            firstPart = fullLine.substring(0, splitIndex).trim();
            secondPart = fullLine.substring(splitIndex).trim();

            while (firstPart.length() < k) {
                firstPart += " ";
            }
        }

        return new String[]{firstPart, secondPart};
    }
}
