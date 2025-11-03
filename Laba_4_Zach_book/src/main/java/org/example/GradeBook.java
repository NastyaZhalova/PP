package org.example;

import java.io.*;
import java.util.*;

public class GradeBook {
    private String lastName;
    private String firstName;
    private String middleName;
    private int course;
    private String group;
    private List<SessionRecord> sessions = new ArrayList<>();

    public GradeBook(String lastName, String firstName, String middleName, int course, String group) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
    }

    public void addSession(SessionRecord session) {
        sessions.add(session);
    }

    public boolean isExcellent() {
        for (SessionRecord session : sessions) {
            if (!session.isExcellent()) return false;
        }
        return true;
    }

    public List<SessionRecord> getSessions() {
        return sessions;
    }

    public String getStudentInfo() {
        return lastName + " " + firstName + " " + middleName + ", курс " + course + ", группа " + group;
    }

    public class SessionRecord {
        private int sessionNumber;
        private Map<String, Integer> exams = new HashMap<>();
        private List<String> passedCredits = new ArrayList<>();

        public SessionRecord(int sessionNumber) {
            this.sessionNumber = sessionNumber;
        }

        public void addExam(String subject, int grade) {
            exams.put(subject, grade);
        }

        public void addCredit(String subject) {
            passedCredits.add(subject);
        }

        public boolean isExcellent() {
            for (int grade : exams.values()) {
                if (grade < 9) return false;
            }
            return true;
        }

        public boolean allCreditsPassed() {
            return !passedCredits.isEmpty(); // можно усложнить при необходимости
        }

        public int getSessionNumber() {
            return sessionNumber;
        }

        public Map<String, Integer> getExams() {
            return exams;
        }
    }
}
