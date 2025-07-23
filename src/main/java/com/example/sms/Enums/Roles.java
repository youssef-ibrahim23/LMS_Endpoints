package com.example.sms.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Roles {
    SCHOOL_ADMIN("SchoolAdmin", "School Administrator"),
    COMPANY_ADMIN("CompanyAdmin", "Company Administrator"),
    TEACHER("Teacher", "Teacher"),
    TRAINER("Trainer", "Trainer"),
    STUDENT("Student", "Student"),
    TRAINEE("Trainee", "Trainee");

    private final String dbValue;
    private final String displayName;

    Roles(String dbValue, String displayName) {
        this.dbValue = dbValue;
        this.displayName = displayName;
    }

    /**
     * Returns the value to be stored in the database
     */
    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    /**
     * Returns a user-friendly display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Creates an enum from a database value
     */
    @JsonCreator
    public static Roles fromDbValue(String dbValue) {
        if (dbValue == null) {
            throw new IllegalArgumentException("DB value cannot be null");
        }

        for (Roles role : values()) {
            if (role.dbValue.equalsIgnoreCase(dbValue)) {
                return role;
            }
        }

        throw new IllegalArgumentException("No role found for db value: " + dbValue);
    }

    /**
     * Validates if a string is a valid database value for roles
     */
    public static boolean isValidRole(String dbValue) {
        try {
            fromDbValue(dbValue);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns all valid database values as an array
     */
    public static String[] validDbValues() {
        Roles[] roles = values();
        String[] values = new String[roles.length];
        for (int i = 0; i < roles.length; i++) {
            values[i] = roles[i].dbValue;
        }
        return values;
    }
}