package org.example;

import java.util.*;

public class TextClass {
    public static List<String> findLongestChain(List<String> words) {
        List<String> maxChain = new ArrayList<>();
        List<String> visited = new ArrayList<>();
        List<String> currentChain = new ArrayList<>();

        for (String word : words) {
            visited.clear();
            currentChain.clear();
            rec(word, words, visited, currentChain, maxChain);
        }

        return maxChain;
    }

    private static void rec(String current, List<String> words, List<String> visited,
                            List<String> currentChain, List<String> maxChain) {
        visited.add(current);
        currentChain.add(current);

        boolean extended = false;
        for (String next : words) {
            if (!visited.contains(next) &&
                    Character.toLowerCase(current.charAt(current.length() - 1)) ==
                            Character.toLowerCase(next.charAt(0))) {
                rec(next, words, visited, currentChain, maxChain);
                extended = true;
            }
        }

        if (!extended && currentChain.size() > maxChain.size()) {
            maxChain.clear();
            maxChain.addAll(currentChain);
        }

        visited.remove(visited.size() - 1);
        currentChain.remove(currentChain.size() - 1);
    }
}
