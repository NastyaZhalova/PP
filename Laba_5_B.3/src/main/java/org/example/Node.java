package org.example;

public class Node implements Comparable<Node> {
    public String word;
    public int freq;
    public Node left, right;

    public Node(String word, int freq) {
        this.word = word;
        this.freq = freq;
    }

    public Node(Node left, Node right) {
        this.word = null;
        this.freq = left.freq + right.freq;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return word != null;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.freq, other.freq);
    }
}
