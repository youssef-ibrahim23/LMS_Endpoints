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

import com.example.sms.Models.Parent;
import com.example.sms.Services.ParentService;
import com.example.sms.Services.ResponseService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;

    // Create a new parent
    @PostMapping("/create")
    public ResponseEntity<?> createParent(@RequestBody Parent parent) {
        try {
            if (parent == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Parent data is required"));
            }
            if (parent.getName() == null || parent.getName().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Name is required"));
            }

            Parent createdParent = parentService.addParent(parent);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Parent created successfully", createdParent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create parent: " + e.getMessage()));
        }
    }

    // Get a parent by ID
   @GetMapping("/{id}")
public ResponseEntity<?> getParentById(@PathVariable Integer id) {
    try {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse("Invalid parent ID"));
        }

        Optional<Parent> parent = parentService.getParentById(id);
        if (parent.isPresent()) {
            return ResponseEntity.ok(parent.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseService.createErrorResponse("Parent not found with ID: " + id));
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseService.createErrorResponse("Failed to retrieve parent: " + e.getMessage()));
    }
}
    // Get all parents
    @GetMapping
    public ResponseEntity<?> getAllParents() {
        try {
            List<Parent> parents = parentService.getAllParents();
            if (parents.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No parents found", null));
            }
            return ResponseEntity.ok(parents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve parents: " + e.getMessage()));
        }
    }

    // Update a parent by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateParent(@PathVariable Integer id, @RequestBody Parent parent) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid parent ID"));
            }
            if (parent == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Parent data is required"));
            }

            Parent updatedParent = parentService.updateParent(id, parent);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Parent updated successfully", updatedParent));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update parent: " + e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        try {
            Long count = parentService.countOfParents();
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Parents count retrieved", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get parents count: " + e.getMessage()));
        }
    }
}