package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<GradeBook> gradeBooks = readFromTextFile("input.txt");

        writeToJson(gradeBooks, "gradebooks.json");

        List<GradeBook> fromJson = readFromJson("gradebooks.json");

        writeExcellentToJson(fromJson, "excellent_students.json");
    }

    public static List<GradeBook> readFromTextFile(String filename) {
        List<GradeBook> gradeBooks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                GradeBook gb = new GradeBook(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4]);
                GradeBook.SessionRecord session = new GradeBook.SessionRecord(Integer.parseInt(parts[5]));

                for (int i = 6; i < parts.length; i++) {
                    if (parts[i].startsWith("Зачет:")) {
                        session.addCredit(parts[i].substring(6));
                    } else {
                        String[] examParts = parts[i].split(":");
                        session.addExam(examParts[0], Integer.parseInt(examParts[1]));
                    }
                }

                gb.getSessions().add(session);
                gradeBooks.add(gb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gradeBooks;
    }

    public static void writeToJson(List<GradeBook> gradeBooks, String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), gradeBooks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<GradeBook> readFromJson(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filename), new TypeReference<List<GradeBook>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static void writeExcellentToJson(List<GradeBook> gradeBooks, String filename) {
        List<Map<String, Object>> output = new ArrayList<>();

        for (GradeBook gb : gradeBooks) {
            if (gb.isExcellent()) {
                for (GradeBook.SessionRecord session : gb.getSessions()) {
                    for (Map.Entry<String, Integer> exam : session.getExams().entrySet()) {
                        Map<String, Object> entry = new LinkedHashMap<>();
                        entry.put("student", gb.getLastName() + " " + gb.getFirstName() + " " + gb.getMiddleName());
                        entry.put("course", gb.getCourse());
                        entry.put("group", gb.getGroup());
                        entry.put("session", session.getSessionNumber());
                        entry.put("exam", exam.getKey());
                        entry.put("grade", exam.getValue());
                        output.add(entry);
                    }
                }
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
