package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "grades")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Integer gradeId;

    @Column(name = "grade_name", nullable = false, unique = true)
    private String gradeName;

    // Constructors
    public Grade() {
    }

    public Grade(String gradeName) {
        this.gradeName = gradeName;
    }

    public Grade(Integer gradeId, String gradeName) {
        this.gradeId = gradeId;
        this.gradeName = gradeName;
    }

    // Getters and Setters
    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    // toString
    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                '}';
    }
}
