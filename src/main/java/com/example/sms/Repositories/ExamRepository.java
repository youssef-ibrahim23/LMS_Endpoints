package com.example.sms.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
}
