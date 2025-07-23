package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "classes")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Integer classId;

    @Column(name = "class_name", nullable = false, unique = true)
    private String className;

    // Constructors
    public SchoolClass() {
    }

    public SchoolClass(String className) {
        this.className = className;
    }

    public SchoolClass(Integer classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    // Getters and Setters
    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    // toString
    @Override
    public String toString() {
        return "SchoolClass{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                '}';
    }
}
