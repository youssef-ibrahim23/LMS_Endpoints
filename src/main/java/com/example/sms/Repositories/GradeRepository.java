package com.example.sms.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
