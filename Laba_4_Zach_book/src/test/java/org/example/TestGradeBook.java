package org.example;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TestGradeBook {

    private static final String INPUT_FILE = "test_input.txt";

    @BeforeEach
    void setup() throws IOException {
        Files.writeString(Path.of(INPUT_FILE),
                "Иванов Иван Иванович 2 БИ-21 1 Математика:9 Информатика:10 Зачет:Физкультура\n" +
                        "Петрова Анна Сергеевна 3 БИ-20 2 Математика:8 Зачет:История\n" +
                        "Смирнов Алексей Петрович 1 БИ-22 1 Алгебра:10 Геометрия:10 Зачет:Физика");
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(Path.of(INPUT_FILE));
    }

    @Test
    void testReadFromTextFile_parsesCorrectly() {
        List<GradeBook> books = Main.readFromTextFile(INPUT_FILE);
        assertEquals(3, books.size());

        GradeBook first = books.get(0);
        assertEquals("Иванов", first.getLastName());
        assertEquals(2, first.getCourse());
        assertEquals("БИ-21", first.getGroup());

        GradeBook.SessionRecord session = first.getSessions().get(0);
        assertEquals(1, session.getSessionNumber());
        assertEquals(2, session.getExams().size());
        assertTrue(session.getExams().containsKey("Математика"));
        assertEquals(10, session.getExams().get("Информатика"));
        assertTrue(session.getPassedCredits().contains("Физкультура"));
    }

    @Test
    void testIsExcellent_logic() {
        List<GradeBook> books = Main.readFromTextFile(INPUT_FILE);

        assertTrue(books.get(0).isExcellent()); // Иванов: 9, 10
        assertFalse(books.get(1).isExcellent()); // Петрова: 8
        assertTrue(books.get(2).isExcellent()); // Смирнов: 10, 10
    }

    @Test
    void testFilterExcellentStudents() {
        List<GradeBook> books = Main.readFromTextFile(INPUT_FILE);
        List<GradeBook> excellent = books.stream().filter(GradeBook::isExcellent).toList();

        assertEquals(2, excellent.size());
        assertEquals("Иванов", excellent.get(0).getLastName());
        assertEquals("Смирнов", excellent.get(1).getLastName());
    }
}
