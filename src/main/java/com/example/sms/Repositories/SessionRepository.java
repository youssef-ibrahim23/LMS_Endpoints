package com.example.sms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sms.Models.Session;

public interface SessionRepository extends JpaRepository<Session, Integer> {
}
