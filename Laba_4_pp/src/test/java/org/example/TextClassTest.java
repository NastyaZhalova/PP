package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TextClassTest {

    @Test
    void testSimpleChain() {
        List<String> input = Arrays.asList("мир", "работа", "апельсин", "ночь", "храм", "молоко");
        List<String> result = TextClass.findLongestChain(input);
        assertEquals(Arrays.asList("храм", "мир", "работа", "апельсин", "ночь"), result);
    }

    @Test
    void testNoChain() {
        List<String> input = Arrays.asList("дом", "кот", "рыба");
        List<String> result = TextClass.findLongestChain(input);
        assertEquals(1, result.size()); // хотя бы одно слово
    }

    @Test
    void testFullChain() {
        List<String> input = Arrays.asList("а", "б", "в", "г");
        List<String> result = TextClass.findLongestChain(input);
        assertEquals(1, result.size()); // нет совпадений по буквам
    }

    @Test
    void testEmptyInput() {
        List<String> input = Arrays.asList();
        List<String> result = TextClass.findLongestChain(input);
        assertTrue(result.isEmpty());
    }
}
