package org.example;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class StringStack {
    private ArrayList<String> stackArray;
    private int top;

    public StringStack() {
        stackArray = new ArrayList<>();
        top = -1;
    }

    public void push(String value) {
        stackArray.add(value);
        top++;
    }

    public String pop() {
        if (isEmpty()) throw new EmptyStackException();
        String value = stackArray.remove(top);
        top--;
        return value;
    }

    public String get(int index) {
        if (index < 0 || index > top) throw new IndexOutOfBoundsException("Invalid index: " + index);
        return stackArray.get(index);
    }

    public void set(int index, String value) {
        if (index < 0 || index > top) throw new IndexOutOfBoundsException("Invalid index: " + index);
        stackArray.set(index, value);
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }
}
