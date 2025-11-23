import org.example.TextJustify;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TextJustifyTest {

    @Test
    public void testSingleLineJustification() {
        List<String> words = Arrays.asList("Привет", "мир");
        String line = TextJustify.justifyLine(words, 15);
        assertEquals(15, line.length(), "Строка должна быть ровно 15 символов");
    }

    @Test
    public void testParagraphIndent() {
        String text = "Это тестовый абзац для проверки.";
        List<String> lines = TextJustify.justifyText(text, 20, true);

        assertTrue(lines.get(0).startsWith("    "), "Первая строка должна начинаться с красной строки (отступа)");
    }

    @Test
    public void testParagraphSplit() {
        List<String> input = Arrays.asList(
                "Первый абзац текста.",
                "",
                "Второй абзац текста."
        );

        List<List<String>> paragraphs = TextJustify.splitIntoParagraphs(input);

        assertEquals(2, paragraphs.size(), "Должно быть два абзаца");
        assertEquals("Первый абзац текста.", paragraphs.get(0).get(0));
        assertEquals("Второй абзац текста.", paragraphs.get(1).get(0));
    }

    @Test
    public void testLastLineNotJustified() {
        String text = "Слова для проверки последней строки";
        List<String> lines = TextJustify.justifyText(text, 25, false);

        String lastLine = lines.get(lines.size() - 1);
        assertTrue(lastLine.trim().length() < 25, "Последняя строка не должна быть полностью выровнена");
    }
}
