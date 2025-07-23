package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "students_parents")
public class StudentParent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "relationship", nullable = false)
    private String relationship;

    // Constructors
    public StudentParent() {
    }

    public StudentParent(Parent parent, Student student, String relationship) {
        this.parent = parent;
        this.student = student;
        this.relationship = relationship;
    }

    public StudentParent(Integer id, Parent parent, Student student, String relationship) {
        this.id = id;
        this.parent = parent;
        this.student = student;
        this.relationship = relationship;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    // toString
    @Override
    public String toString() {
        return "StudentParent{" +
                "id=" + id +
                ", parent=" + (parent != null ? parent.getParentId() : null) +
                ", student=" + (student != null ? student.getStudentId() : null) +
                ", relationship='" + relationship + '\'' +
                '}';
    }
}
