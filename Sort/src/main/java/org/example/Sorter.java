package org.example;

public class Sorter {
    public static int[] bubbleSort(int[] arr) {
        int[] copy = arr.clone();
        int n = copy.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (copy[j] > copy[j + 1]) {
                    int t = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = t;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        return copy;
    }
}
