package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.StudentParent;

public interface StudentParentRepository extends JpaRepository<StudentParent, Integer> {
}
