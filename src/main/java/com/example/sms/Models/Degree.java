package com.example.sms.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="degrees")
public class Degree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer degreeId;

    @ManyToOne
    @JoinColumn(name="exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name="student_id")
    private User student;

    private Integer degree;
    private String value;
    public Integer getDegreeId() {
        return degreeId;
    }
    public void setDegreeId(Integer degreeId) {
        this.degreeId = degreeId;
    }
    public Exam getExam() {
        return exam;
    }
    public void setExam(Exam exam) {
        this.exam = exam;
    }
    public User getStudent() {
        return student;
    }
    public void setStudent(User student) {
        this.student = student;
    }
    public Integer getDegree() {
        return degree;
    }
    public void setDegree(Integer degree) {
        this.degree = degree;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
}
