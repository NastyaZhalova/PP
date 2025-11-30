package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AllTests {

    @Test
    void testAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] result = Sorter.bubbleSort(input);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }

    @Test
    void testReverseOrder() {
        int[] input = {5, 4, 3, 2, 1};
        int[] result = Sorter.bubbleSort(input);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result);
    }

    @Test
    void testWithDuplicates() {
        int[] input = {3, 1, 2, 3, 1};
        int[] result = Sorter.bubbleSort(input);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3}, result);
    }

    @Test
    void testEmptyArray() {
        int[] input = {};
        int[] result = Sorter.bubbleSort(input);
        assertArrayEquals(new int[]{}, result);
    }

    @Test
    void testSingleElement() {
        int[] input = {42};
        int[] result = Sorter.bubbleSort(input);
        assertArrayEquals(new int[]{42}, result);
    }

    @Test
    void testGenerateDataSizeAndRange() {
        VisualSort vs = new VisualSort();
        int[] arr = vs.generateData(20);
        assertEquals(20, arr.length);
        for (int val : arr) {
            assertTrue(val >= 10 && val <= 100);
        }
    }

    @Test
    void testBarsPanelSetData() {
        int[] arr = {10, 20, 30};
        VisualSort.BarsPanel panel = new VisualSort.BarsPanel(arr);
        int[] newArr = {40, 50};
        panel.setData(newArr);
        assertEquals(2, newArr.length);
    }

    @Test
    void testBarsPanelHighlightAndClear() {
        int[] arr = {1, 2, 3};
        VisualSort.BarsPanel panel = new VisualSort.BarsPanel(arr);
        panel.setHighlight(0, 1);
        panel.clearHighlight();
        assertNotNull(panel);
    }

    @Test
    void testBarsPanelSortedTail() {
        int[] arr = {1, 2, 3};
        VisualSort.BarsPanel panel = new VisualSort.BarsPanel(arr);
        panel.setSortedTailLength(2);
        assertNotNull(panel);
    }

    @Test
    void testSleepBySpeed() {
        VisualSort vs = new VisualSort();
        vs.speedSlider.setValue(100);
        vs.sleepBySpeed();
    }
}
