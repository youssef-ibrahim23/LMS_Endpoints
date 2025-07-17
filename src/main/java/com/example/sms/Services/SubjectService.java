package com.example.sms.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Subject;
import com.example.sms.Repositories.SubjectRepository;

@Service
public class SubjectService {
    @Autowired

    private SubjectRepository subjectRepository;

    // Method to add a new subject
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Long getCountOfSubjects() {
        return subjectRepository.count();
    }
    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }
    public int updateSubject(int subjectID, Subject newSubject){
        Optional<Subject> exists = subjectRepository.findById(subjectID);
        if(exists.isPresent()){
            Subject subject = exists.get();
            subject.setSubjectName(newSubject.getSubjectName());
            subjectRepository.save(subject);
            return 1;
        }
        return 0;
    }
}
