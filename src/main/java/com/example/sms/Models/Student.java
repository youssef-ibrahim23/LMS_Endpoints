package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private SchoolClass studentClass;

    // Constructors
    public Student() {
    }

    public Student(User user, Grade grade, SchoolClass studentClass) {
        this.user = user;
        this.grade = grade;
        this.studentClass = studentClass;
    }

    public Student(Integer studentId, User user, Grade grade, SchoolClass studentClass) {
        this.studentId = studentId;
        this.user = user;
        this.grade = grade;
        this.studentClass = studentClass;
    }

    // Getters and Setters
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public SchoolClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(SchoolClass studentClass) {
        this.studentClass = studentClass;
    }

    // toString
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", userId=" + (user != null ? user.getUserId() : null) +
                ", grade=" + (grade != null ? grade.getGradeName() : null) +
                ", class=" + (studentClass != null ? studentClass.getClassName() : null) +
                '}';
    }
}
