package com.example.sms.Models;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "national_id", unique = true)
    private String nationalId;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted = false;

    @Column(name = "joined_on", columnDefinition = "date default CURRENT_DATE")
    private Date joinedOn;

    @Column(name = "left_on")
    private Date leftOn;

    // Constructors
    public User() {
    }

    public User(Role role, String firstName, String middleName, String lastName, String gender,
                String address, Date birthDate, String phoneNumber, byte[] photo,
                String nationalId, Boolean isDeleted, Date joinedOn, Date leftOn) {
        this.role = role;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
        this.nationalId = nationalId;
        this.isDeleted = isDeleted;
        this.joinedOn = joinedOn;
        this.leftOn = leftOn;
    }

    // Getters and Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    public Date getLeftOn() {
        return leftOn;
    }

    public void setLeftOn(Date leftOn) {
        this.leftOn = leftOn;
    }

    // toString
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", role=" + (role != null ? role.getRoleName() : "null") +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", isDeleted=" + isDeleted +
                ", joinedOn=" + joinedOn +
                ", leftOn=" + leftOn +
                '}';
    }
}
