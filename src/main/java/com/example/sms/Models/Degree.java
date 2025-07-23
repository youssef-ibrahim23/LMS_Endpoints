package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "degrees")
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "degree_id")
    private Integer degreeId;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "degree", nullable = false)
    private Integer degree;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "value")
    private String value;

    // Getters and Setters
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
