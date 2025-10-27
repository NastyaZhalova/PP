package org.example;
import java.io.*;
import java.util.*;

public class File {

    public static List<Student> read(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            Student s = Student.fromString(line);
            students.add(s);
        }

        reader.close();
        return students;
    }

    public static void write(String filename, List<Student> students) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true));

        for (Student s : students) {
            writer.write(s.toString());
            writer.newLine();
        }
        writer.newLine();
        writer.close();
    }
}
