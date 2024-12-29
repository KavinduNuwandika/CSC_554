package com.GSCOMP230.Student_Managment.DTO;

import org.springframework.stereotype.Service;

@Service
public class GradeCalculator {

    // Method to calculate grade based on marks
    public String calculateGrade(Integer marks) {
        if (marks >= 90) {
            return "A+";
        } else if (marks >= 80) {
            return "A";
        } else if (marks >= 70) {
            return "B";
        } else if (marks >= 60) {
            return "C";
        } else if (marks >= 50) {
            return "D";
        } else {
            return "F";
        }
    }
}