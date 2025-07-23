package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Integer teacherId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @Column(name = "qualification")
    private String qualification;

    // Constructors
    public Teacher() {
    }

    public Teacher(User user, String specialization, Integer yearsOfExperience, String qualification) {
        this.user = user;
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.qualification = qualification;
    }

    public Teacher(Integer teacherId, User user, String specialization, Integer yearsOfExperience, String qualification) {
        this.teacherId = teacherId;
        this.user = user;
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        this.qualification = qualification;
    }

    // Getters and Setters
    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    // toString
    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", userId=" + (user != null ? user.getUserId() : null) +
                ", specialization='" + specialization + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", qualification='" + qualification + '\'' +
                '}';
    }
}
