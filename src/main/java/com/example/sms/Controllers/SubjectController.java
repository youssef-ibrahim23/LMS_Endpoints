package com.example.sms.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sms.Models.Subject;
import com.example.sms.Services.SubjectService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public Subject createSubject(@RequestBody Subject subject) {
        return subjectService.addSubject(subject);
    }
    @GetMapping
    public List<Subject> getAll(){
        return subjectService.getSubjects();
    }
    @GetMapping("/count")
    public Long getCount(){
        return subjectService.getCountOfSubjects();
    }
    @PutMapping("/{id}")
    public int editSubject(@PathVariable int id, @RequestBody Subject subject){
        return subjectService.updateSubject(id, subject);
    }
}
