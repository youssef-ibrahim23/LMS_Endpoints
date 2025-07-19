package com.example.sms.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sms.Models.Parent;
import com.example.sms.Repositories.ParentRepository;

@Service
public class ParentService {

    @Autowired
    private ParentRepository parentRepository;

    // Create a new parent record
    public Parent addParent(Parent parent) {
        return parentRepository.save(parent);
    }

    // Retrieve a parent by its ID
    public Optional<Parent> getParentById(Integer id) {
        return parentRepository.findById(id);
    }

    // Retrieve all parents
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    // Update an existing parent record
    public String updateParent(Integer id, Parent updatedParent) {
        return parentRepository.findById(id).map(parent -> {
            parent.setName(updatedParent.getName());
            parent.setEmail(updatedParent.getEmail());
            parent.setPhoneNumber(updatedParent.getPhoneNumber());
            parent.setAddress(updatedParent.getAddress());
            parent.setGender(updatedParent.getGender());
            parentRepository.save(parent);
            return "Parent is updated successfully";
        }).orElseThrow(() -> new RuntimeException("Parent not found with id: " + id));
    }

    public Long countOfParents(){
        return parentRepository.count();
    }
}
