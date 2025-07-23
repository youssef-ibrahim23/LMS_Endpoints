package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "grades_subjects")
public class GradeSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_subject_id")
    private Integer gradeSubjectId;

    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // Constructors
    public GradeSubject() {
    }

    public GradeSubject(Grade grade, Subject subject, Teacher teacher) {
        this.grade = grade;
        this.subject = subject;
        this.teacher = teacher;
    }

    public GradeSubject(Integer gradeSubjectId, Grade grade, Subject subject, Teacher teacher) {
        this.gradeSubjectId = gradeSubjectId;
        this.grade = grade;
        this.subject = subject;
        this.teacher = teacher;
    }

    // Getters and Setters
    public Integer getGradeSubjectId() {
        return gradeSubjectId;
    }

    public void setGradeSubjectId(Integer gradeSubjectId) {
        this.gradeSubjectId = gradeSubjectId;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    // toString
    @Override
    public String toString() {
        return "GradeSubject{" +
                "gradeSubjectId=" + gradeSubjectId +
                ", grade=" + (grade != null ? grade.getGradeName() : "null") +
                ", subject=" + (subject != null ? subject.getSubjectName() : "null") +
                ", teacher=" + (teacher != null ? teacher.getTeacherId() : "null") +
                '}';
    }
}
