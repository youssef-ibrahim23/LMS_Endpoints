package com.example.sms.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Degree;
import com.example.sms.Services.DegreeService;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/api/v1/degrees")
public class DegreeController {

    @Autowired
    private DegreeService degreeService;

    // Create a new degree
    @PostMapping("/create")
    public ResponseEntity<Degree> createDegree(@RequestBody Degree degree) {
        return ResponseEntity.ok(degreeService.createDegree(degree));
    }

    // Get a degree by ID
    @GetMapping("/{id}")
    public ResponseEntity<Degree> getDegreeById(@PathVariable Integer id) {
        return degreeService.getDegreeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all degrees
    @GetMapping("/all")
    public ResponseEntity<List<Degree>> getAllDegrees() {
        return ResponseEntity.ok(degreeService.getAllDegrees());
    }

    // Update a degree by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Degree> updateDegree(@PathVariable Integer id, @RequestBody Degree degree) {
        return ResponseEntity.ok(degreeService.updateDegree(id, degree));
    }

}
