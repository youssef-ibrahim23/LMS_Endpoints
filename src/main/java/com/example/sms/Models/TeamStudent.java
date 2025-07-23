package com.example.sms.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "team_students")
public class TeamStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_student_id")
    private Integer teamStudentId;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "role", nullable = false)
    private String role;

    // Getters and Setters

    public Integer getTeamStudentId() {
        return teamStudentId;
    }

    public void setTeamStudentId(Integer teamStudentId) {
        this.teamStudentId = teamStudentId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
