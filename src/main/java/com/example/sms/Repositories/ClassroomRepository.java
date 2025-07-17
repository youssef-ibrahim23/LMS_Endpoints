package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
}
