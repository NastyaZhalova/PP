package org.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class GradeBook {
    private String lastName;
    private String firstName;
    private String middleName;
    private int course;
    private String group;
    private List<SessionRecord> sessions = new ArrayList<>();

    @JsonCreator
    public GradeBook(
            @JsonProperty("lastName") String lastName,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("middleName") String middleName,
            @JsonProperty("course") int course,
            @JsonProperty("group") String group) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
    }

    public boolean isExcellent() {
        for (SessionRecord session : sessions) {
            if (!session.isExcellent()) return false;
        }
        return true;
    }

    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public int getCourse() { return course; }
    public String getGroup() { return group; }
    public List<SessionRecord> getSessions() { return sessions; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SessionRecord {
        private int sessionNumber;
        private Map<String, Integer> exams = new HashMap<>();
        private List<String> passedCredits = new ArrayList<>();

        @JsonCreator
        public SessionRecord(@JsonProperty("sessionNumber") int sessionNumber) {
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

        public int getSessionNumber() { return sessionNumber; }
        public Map<String, Integer> getExams() { return exams; }
        public List<String> getPassedCredits() { return passedCredits; }
    }
}
