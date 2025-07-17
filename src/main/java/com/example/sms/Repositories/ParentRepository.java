package com.example.sms.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Parent;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
}

