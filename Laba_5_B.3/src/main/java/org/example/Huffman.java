package org.example;

import java.util.*;

public class Huffman {

       public Map<String, String> buildHuffmanCodes(List<String> words) {
        Map<String, Integer> freqMap = new HashMap<>();
        for (String word : words) {
            freqMap.put(word, freqMap.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Map.Entry<String, Integer> entry : freqMap.entrySet()) {
            queue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            Node a = queue.poll();
            Node b = queue.poll();
            queue.add(new Node(a, b));
        }

        Node root = queue.poll();
        Map<String, String> codes = new HashMap<>();
        buildCodesRecursive(root, "", codes);
        return codes;
    }

    private void buildCodesRecursive(Node node, String code, Map<String, String> map) {
        if (node.isLeaf()) {
            map.put(node.word, code.isEmpty() ? "0" : code);
        } else {
            buildCodesRecursive(node.left, code + "0", map);
            buildCodesRecursive(node.right, code + "1", map);
        }
    }

    public List<String> compress(List<String> words, Map<String, String> codes) {
        List<String> compressed = new ArrayList<>();
        for (String word : words) {
            compressed.add(codes.get(word));
        }
        return compressed;
    }
}
