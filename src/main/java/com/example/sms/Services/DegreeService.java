package com.example.sms.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Degree;
import com.example.sms.Repositories.DegreeRepository;

@Service
public class DegreeService {

    @Autowired
    private DegreeRepository degreeRepository;

    // Create a new degree record
    public Degree createDegree(Degree degree) {
        return degreeRepository.save(degree);
    }

    // Retrieve a degree record by its ID
    public Optional<Degree> getDegreeById(Integer id) {
        return degreeRepository.findById(id);
    }

    // Retrieve all degree records
    public List<Degree> getAllDegrees() {
        return degreeRepository.findAll();
    }

    // Update an existing degree record
    public Degree updateDegree(Integer id, Degree updatedDegree) {
        return degreeRepository.findById(id).map(degree -> {
            degree.setExam(updatedDegree.getExam());
            degree.setStudent(updatedDegree.getStudent());
            degree.setDegree(updatedDegree.getDegree());
            degree.setValue(updatedDegree.getValue());
            return degreeRepository.save(degree);
        }).orElseThrow(() -> new RuntimeException("Degree not found with id: " + id));
    }

}
