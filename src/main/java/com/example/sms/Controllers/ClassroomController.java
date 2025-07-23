package com.example.sms.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sms.Models.SchoolClass;
import com.example.sms.Services.ClassroomService;
import com.example.sms.Services.ResponseService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/classes")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;
    // @Autowired
    // private ResponseService responseService;
    @PostMapping("/create")
    public ResponseEntity<?> createClassroom(@RequestBody SchoolClass classroom) {
        try {
            if (classroom == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Classroom data is required"));
            }
            if (classroom.getClassName() == null || classroom.getClassName().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Classroom name is required"));
            }

            SchoolClass createdClassroom = classroomService.createClassroom(classroom);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseService.createSuccessResponse("Classroom created successfully", createdClassroom));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to create classroom: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClassroomById(@PathVariable Integer id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid classroom ID"));
            }

            Optional<SchoolClass> classroom = classroomService.getSchoolClassById(id);
            if (classroom.isPresent()) {
                return ResponseEntity.ok(classroom.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseService.createErrorResponse("Classroom not found with ID: " + id));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve classroom: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllClassrooms() {
        try {
            List<SchoolClass> classrooms = classroomService.getAllSchoolClasss();
            if (classrooms.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(ResponseService.createSuccessResponse("No classrooms found", null));
            }
            return ResponseEntity.ok(classrooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to retrieve classrooms: " + e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClassroom(@PathVariable Integer id, @RequestBody SchoolClass classroom) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Invalid classroom ID"));
            }
            if (classroom == null) {
                return ResponseEntity.badRequest()
                    .body(ResponseService.createErrorResponse("Classroom data is required"));
            }

            SchoolClass updatedClassroom = classroomService.updateSchoolClass(id, classroom);
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Classroom updated successfully", updatedClassroom));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(ResponseService.createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to update classroom: " + e.getMessage()));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getCount() {
        try {
            Long count = classroomService.getCountOfClasses();
            return ResponseEntity.ok(ResponseService.createSuccessResponse("Class count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseService.createErrorResponse("Failed to get classroom count: " + e.getMessage()));
        }
    }

    
}