package org.example;

public class Student {
    public long num;
    public String name;
    public int group;
    public double grade;

    public Student(long num, String name, int group, double grade) {
        this.num = num;
        this.name = name;
        this.group = group;
        this.grade = grade;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Student other) {
            return this.num == other.num;
        }
        return false;
    }

    public int hashCode() {
        return Long.hashCode(num);
    }

    public String toString() {
        return num + " " + name + " " + group + " " + grade;
    }

    public static Student fromString(String line) {
        String[] parts = line.trim().split("\\s+");
        return new Student(
                Long.parseLong(parts[0]),
                parts[1],
                Integer.parseInt(parts[2]),
                Double.parseDouble(parts[3])
        );
    }
}

