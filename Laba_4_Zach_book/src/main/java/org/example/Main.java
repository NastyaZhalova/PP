package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<GradeBook> gradeBooks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                GradeBook gb = new GradeBook(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4]);
                GradeBook.SessionRecord session = gb.new SessionRecord(Integer.parseInt(parts[5]));

                for (int i = 6; i < parts.length; i++) {
                    if (parts[i].startsWith("Зачет:")) {
                        session.addCredit(parts[i].substring(6));
                    } else {
                        String[] examParts = parts[i].split(":");
                        session.addExam(examParts[0], Integer.parseInt(examParts[1]));
                    }
                }

                gb.addSession(session);
                gradeBooks.add(gb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (GradeBook gb : gradeBooks) {
                if (gb.isExcellent()) {
                    for (GradeBook.SessionRecord session : gb.getSessions()) {
                        for (Map.Entry<String, Integer> exam : session.getExams().entrySet()) {
                            writer.write(gb.getStudentInfo() + ", сессия " + session.getSessionNumber() +
                                    ", предмет: " + exam.getKey() + ", оценка: " + exam.getValue());
                            writer.newLine();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
