package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringClassTest {

    @Test
    public void testShortLine() {
        String[] result = StringClass.splitLine("Привет", 10);
        assertEquals("Привет    ", result[0]);
        assertEquals("", result[1]);
    }

    @Test
    public void testExactSplit() {
        String[] result = StringClass.splitLine("Привет мир", 6);
        assertEquals("Привет", result[0]);
        assertEquals("мир", result[1]);
    }

    @Test
    public void testSplitInsideWord() {
        String[] result = StringClass.splitLine("Приветмир", 6);
        assertEquals("      ", result[0]);
        assertEquals("Приветмир", result[1]);
    }

    @Test
    public void testSplitWithPunctuation() {
        String[] result = StringClass.splitLine("Привет, мир.", 7);
        assertEquals("Привет ", result[0]);
        assertEquals("мир", result[1]);
    }

    @Test
    public void testMultipleSpacesAndDots() {
        String[] result = StringClass.splitLine("Привет   мир. Как дела?", 10);
        assertEquals("Привет   ", result[0]);
        assertEquals("мир Как дела", result[1]);
    }
}
