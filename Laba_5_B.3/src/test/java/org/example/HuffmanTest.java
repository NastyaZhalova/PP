package org.example;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class HuffmanTest {

    Huffman compressor = new Huffman();

    @Test
    public void testBuildHuffmanCodes_containsAllWords() {
        List<String> input = Arrays.asList("a", "b", "a", "c", "a", "b");
        Map<String, String> codes = compressor.buildHuffmanCodes(input);

        assertTrue(codes.containsKey("a"));
        assertTrue(codes.containsKey("b"));
        assertTrue(codes.containsKey("c"));
    }

    @Test
    public void testBuildHuffmanCodes_uniqueCodes() {
        List<String> input = Arrays.asList("x", "y", "x", "z", "x", "z");
        Map<String, String> codes = compressor.buildHuffmanCodes(input);

        Set<String> uniqueCodes = new HashSet<>(codes.values());
        assertEquals(codes.size(), uniqueCodes.size(), "Коды не уникальны");
    }

    @Test
    public void testCompress_outputSizeMatchesInput() {
        List<String> input = Arrays.asList("one", "two", "three", "one", "two");
        Map<String, String> codes = compressor.buildHuffmanCodes(input);
        List<String> compressed = compressor.compress(input, codes);

        assertEquals(input.size(), compressed.size(), "Размер сжатого текста не совпадает");
    }

    @Test
    public void testCompress_codesAreNotEmpty() {
        List<String> input = Arrays.asList("red", "blue", "green", "red", "blue");
        Map<String, String> codes = compressor.buildHuffmanCodes(input);
        List<String> compressed = compressor.compress(input, codes);

        for (String code : compressed) {
            assertNotNull(code);
            assertFalse(code.isEmpty(), "Код пустой");
        }
    }

    @Test
    public void testSingleWordInput_generatesSingleCode() {
        List<String> input = Arrays.asList("solo", "solo", "solo");
        Map<String, String> codes = compressor.buildHuffmanCodes(input);
        List<String> compressed = compressor.compress(input, codes);

        assertEquals(1, codes.size(), "Ожидался один код");
        assertEquals(3, compressed.size(), "Ожидалось 3 элемента");
        assertTrue(codes.containsKey("solo"));
        assertTrue(codes.get("solo").matches("[01]+"), "Код должен быть двоичным");
    }
}
