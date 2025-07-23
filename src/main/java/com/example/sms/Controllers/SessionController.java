package com.example.sms.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.GradeSubject;
import com.example.sms.Models.Session;
import com.example.sms.Repositories.GradeSubjectRepository;
import com.example.sms.Services.ResponseService;
import com.example.sms.Services.SessionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private GradeSubjectRepository gradeSubjectRepository;

    @GetMapping
    public ResponseEntity<?> getAllSessions() {
        try {
            List<Session> sessions = sessionService.getAllSessions();
            if (sessions.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No sessions found", null));
            }
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve sessions: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid session ID"));
            }

            Optional<Session> session = sessionService.getSessionById(id);
            if (session.isPresent()) {
                return ResponseEntity.ok(session.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("Session not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve session: " + e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody Session session) {
        try {
            if (session == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Session data is required"));
            }

            if (session.getGradeSubject() == null || session.getGradeSubject().getGradeSubjectId() == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("GradeSubject ID is required"));
            }

            Integer gsId = session.getGradeSubject().getGradeSubjectId();
            GradeSubject fullGS = gradeSubjectRepository.findById(gsId)
                .orElseThrow(() -> new IllegalArgumentException("GradeSubject not found with ID: " + gsId));
            
            session.setGradeSubject(fullGS);
            Session savedSession = sessionService.saveSession(session);
            
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Session created successfully", savedSession));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create session: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSession(@PathVariable Integer id, @RequestBody Session session) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid session ID"));
            }
            if (session == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Session data is required"));
            }

            if (session.getGradeSubject() == null || session.getGradeSubject().getGradeSubjectId() == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("GradeSubject ID is required"));
            }

            Integer gsId = session.getGradeSubject().getGradeSubjectId();
            GradeSubject fullGS = gradeSubjectRepository.findById(gsId)
                .orElseThrow(() -> new IllegalArgumentException("GradeSubject not found with ID: " + gsId));
            
            session.setGradeSubject(fullGS);
            session.setSessionId(id);
            Session updatedSession = sessionService.saveSession(session);
            
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Session updated successfully", updatedSession));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update session: " + e.getMessage()));
        }
    }

    @GetMapping("/today/{id}")
    public List<Session> getTodaySessionsByStudentId(@PathVariable Integer id){

        return sessionService.getTodaySessionsByStudentId(id);
    }
}