package com.example.sms.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sms.Models.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
