package com.example.sms.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sms.Models.Degree;
import com.example.sms.Services.DegreeService;
import com.example.sms.Services.ResponseService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/degrees")
public class DegreeController {

    @Autowired
    private DegreeService degreeService;

    @PostMapping("/create")
    public ResponseEntity<?> createDegree(@RequestBody Degree degree) {
        try {
            if (degree == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Degree data is required"));
            }
            if (degree.getValue() == null || degree.getValue().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Degree value is required"));
            }

            Degree createdDegree = degreeService.createDegree(degree);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Degree created successfully", createdDegree));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create degree: " + e.getMessage()));
        }
    }

@GetMapping("/{id}")
public ResponseEntity<?> getDegreeById(@PathVariable Integer id) {
    try {
        // Validate input
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse("Invalid degree ID"));
        }

        // Get degree from service
        Optional<Degree> degree = degreeService.getDegreeById(id);
        
        // Return appropriate response
        if (degree.isPresent()) {
            return ResponseEntity.ok(degree.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseService.createErrorResponse("Degree not found with ID: " + id));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseService.createErrorResponse("Failed to retrieve degree: " + e.getMessage()));
    }
}

    @GetMapping("/all")
    public ResponseEntity<?> getAllDegrees() {
        try {
            List<Degree> degrees = degreeService.getAllDegrees();
            if (degrees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No degrees found", null));
            }
            return ResponseEntity.ok(degrees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve degrees: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDegree(@PathVariable Integer id, @RequestBody Degree degree) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid degree ID"));
            }
            if (degree == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Degree data is required"));
            }

            Degree updatedDegree = degreeService.updateDegree(id, degree);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Degree updated successfully", updatedDegree));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update degree: " + e.getMessage()));
        }
    }
@GetMapping("/recent-degrees/{id}")
    public List<Degree> findRecentDegreesByStudentId(int id){
        return degreeService.findRecentDegreesByStudentId(id);
    }

    @GetMapping("/student/{id}")
    public List<Degree> findByStudentStudentId(@PathVariable int id){
        return degreeService.findByStudentStudentId(id);
    }
    
    
}