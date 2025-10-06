package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringStackTest {

    @Test
    void testPushAndSize() {
        StringStack stack = new StringStack();
        stack.push("A");
        stack.push("B");
        assertEquals(2, stack.size());
    }

    @Test
    void testGetAndSet() {
        StringStack stack = new StringStack();
        stack.push("X");
        stack.push("Y");
        stack.set(1, "Z");
        assertEquals("Z", stack.get(1));
    }

    @Test
    void testPop() {
        StringStack stack = new StringStack();
        stack.push("first");
        stack.push("second");
        String popped = stack.pop();
        assertEquals("second", popped);
        assertEquals(1, stack.size());
    }

    @Test
    void testIsEmpty() {
        StringStack stack = new StringStack();
        assertTrue(stack.isEmpty());
        stack.push("not empty");
        assertFalse(stack.isEmpty());
    }
}
