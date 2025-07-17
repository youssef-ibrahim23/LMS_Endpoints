package com.example.sms.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name="students")
public class Student {
    @Id
    private Integer studentId;

    @OneToOne
    @MapsId
    @JoinColumn(name="student_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name="class_id")
    private Classroom classroom;

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

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
    
}
