package com.example.sms.Services;

import com.example.sms.DTO.UserDTO;
import com.example.sms.Models.Role;
import com.example.sms.Models.User;
import com.example.sms.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByIdOrThrow(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public List<User> getUsersByGrade(Integer gradeId) {
        return userRepository.findUsersByGrade(gradeId);
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();

        user.setRole(userDTO.getRole()); 
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setGender(userDTO.getGender()); 
        user.setAddress(userDTO.getAddress());
        user.setBirthDate(userDTO.getBirthDate());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setNationalId(userDTO.getNationalId());
        user.setIsDeleted(false);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRoleName(String role) {
        return userRepository.findByRoleName(role);
    }

    public List<User> getUsersByRoleId(Integer roleId) {
        return userRepository.findByRoleId(roleId);
    }

    public List<Map<String, Long>> countOfGrades(){
        return userRepository.getGradeCounts();
    }

    public Long getCountOfUsers() {
        return userRepository.count();
    }

    public Long getCountOfStudents() {
        return userRepository.countOfStudents();
    }

    public Long getCountOfTeachers() {
        return userRepository.countOfTeachers();
    }

    public List<Map<String, Long>> countUsersByGrade() {
        return userRepository.getGradeCounts();
    }

    public int deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public boolean updateUser(Integer id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isPresent()) {
            User user = existingUserOpt.get();

            user.setRole(updatedUser.getRole());
            user.setFirstName(updatedUser.getFirstName());
            user.setMiddleName(updatedUser.getMiddleName());
            user.setLastName(updatedUser.getLastName());
            user.setGender(updatedUser.getGender());
            user.setAddress(updatedUser.getAddress());
            user.setBirthDate(updatedUser.getBirthDate());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setPhoto(updatedUser.getPhoto());
            user.setNationalId(updatedUser.getNationalId());
            user.setIsDeleted(updatedUser.getIsDeleted());

            userRepository.save(user);
            return true;
        }
        return false;
    }

    public List<User> getUsersWithoutAccounts() {
        return userRepository.findUsersWithNoAccounts();
    }

    public List<User> getNonStudent() {
        return userRepository.findNonStudents();
    }
}
