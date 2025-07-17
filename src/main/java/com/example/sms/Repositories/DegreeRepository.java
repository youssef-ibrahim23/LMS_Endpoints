package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Degree;

public interface DegreeRepository extends JpaRepository<Degree, Integer> {
}
