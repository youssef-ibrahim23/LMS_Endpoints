package com.example.sms.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(10) default 'Active'")
    private String status = "Active";

    @Column(name = "created_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "last_reset")
    private Timestamp lastReset;

    // Constructors
    public Account() {
    }

    public Account(User user, String email, String password, String status, Timestamp createdAt, Timestamp lastReset) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
        this.lastReset = lastReset;
    }

    // Getters and Setters

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastReset() {
        return lastReset;
    }

    public void setLastReset(Timestamp lastReset) {
        this.lastReset = lastReset;
    }

    // toString
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", user=" + (user != null ? user.getUserId() : null) +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", lastReset=" + lastReset +
                '}';
    }
}
