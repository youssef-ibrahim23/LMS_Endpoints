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

import com.example.sms.Models.Classroom;
import com.example.sms.Services.ClassroomService;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/api/v1/classes")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    // Create a new classroom
    @PostMapping("/create")
    public ResponseEntity<Classroom> createClassroom(@RequestBody Classroom classroom) {
        return ResponseEntity.ok(classroomService.createClassroom(classroom));
    }

    // Get a classroom by ID
    @GetMapping("/{id}")
    public ResponseEntity<Classroom> getClassroomById(@PathVariable Integer id) {
        return classroomService.getClassroomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all classrooms
    @GetMapping("/all")
    public ResponseEntity<List<Classroom>> getAllClassrooms() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    // Update a classroom by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Classroom> updateClassroom(@PathVariable Integer id, @RequestBody Classroom classroom) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, classroom));
    }

    @GetMapping("/count")
    public Long getCount(){
        return classroomService.getCountOfClasses();
    }
}
