package com.example.sms.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.sms.Models.GradeSubject;
import com.example.sms.Models.Session;
import com.example.sms.Repositories.GradeSubjectRepository;
import com.example.sms.Services.SessionService;

import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private GradeSubjectRepository gradeSubjectRepository;

    @GetMapping
    public List<Session> getAllSessions() {
        return sessionService.getAllSessions();
    }

    @GetMapping("/{id}")
    public Optional<Session> getSessionById(@PathVariable Integer id) {
        return sessionService.getSessionById(id);
    }

    @PostMapping
    public ResponseEntity<String> createSession(@RequestBody Session session) {
        // Fetch full GradeSubject if present
        if (session.getGradeSubject() != null && session.getGradeSubject().getGradeSubjectId() != null) {
            Integer gsId = session.getGradeSubject().getGradeSubjectId();
            GradeSubject fullGS = gradeSubjectRepository.findById(gsId).orElse(null);
            session.setGradeSubject(fullGS);
        }

        sessionService.saveSession(session);
        return ResponseEntity.ok("Session saved successfully.");
    }
}
