package com.example.sms.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Session;
import com.example.sms.Repositories.SessionRepository;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Optional<Session> getSessionById(Integer id) {
        return sessionRepository.findById(id);
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

}
