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

import com.example.sms.Models.Parent;
import com.example.sms.Services.ParentService;

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/api/v1/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;

    // Create a new parent
    @PostMapping("/create")
    public ResponseEntity<Parent> createParent(@RequestBody Parent parent) {
        return ResponseEntity.ok(parentService.addParent(parent));
    }

    // Get a parent by ID
    @GetMapping("/{id}")
    public ResponseEntity<Parent> getParentById(@PathVariable Integer id) {
        return parentService.getParentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all parents
    @GetMapping
    public ResponseEntity<List<Parent>> getAllParents() {
        return ResponseEntity.ok(parentService.getAllParents());
    }

    // Update a parent by ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateParent(@PathVariable Integer id, @RequestBody Parent parent) {
        parentService.updateParent(id, parent);
        return ResponseEntity.ok("Parent updated sucessfully");
    }
    @GetMapping("/count")
    public Long getCount(){
        return parentService.countOfParents();
    }
}
