package com.example.sms.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Exam;
import com.example.sms.Repositories.ExamRepository;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    // Create a new exam record
    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    // Retrieve an exam by its ID
    public Optional<Exam> getExamById(Integer id) {
        return examRepository.findById(id);
    }

    // Retrieve all exams
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    // Update an existing exam record
    public Exam updateExam(Integer id, Exam updatedExam) {
        return examRepository.findById(id).map(exam -> {
            exam.setGradeSubject(updatedExam.getGradeSubject());
            exam.setExamDate(updatedExam.getExamDate());
            exam.setStartTime(updatedExam.getStartTime());
            exam.setEndTime(updatedExam.getEndTime());
            exam.setMaxDegree(updatedExam.getMaxDegree());
            exam.setSuccessDegree(updatedExam.getSuccessDegree());
            exam.setType(updatedExam.getType());
            return examRepository.save(exam);
        }).orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
    }

    // Delete an exam by its ID
    public void deleteExam(Integer id) {
        if (!examRepository.existsById(id)) {
            throw new RuntimeException("Exam not found with id: " + id);
        }
        examRepository.deleteById(id);
    }
}
