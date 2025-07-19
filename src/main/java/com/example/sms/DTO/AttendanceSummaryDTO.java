package com.example.sms.DTO;

public class AttendanceSummaryDTO {
    private Integer studentId;
    private Double presentPercentage;
    private Double absentPercentage;
    private Double latePercentage;

    public AttendanceSummaryDTO(Integer studentId, Double present, Double absent, Double late) {
        this.studentId = studentId;
        this.presentPercentage = present;
        this.absentPercentage = absent;
        this.latePercentage = late;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Double getPresentPercentage() {
        return presentPercentage;
    }

    public void setPresentPercentage(Double presentPercentage) {
        this.presentPercentage = presentPercentage;
    }

    public Double getAbsentPercentage() {
        return absentPercentage;
    }

    public void setAbsentPercentage(Double absentPercentage) {
        this.absentPercentage = absentPercentage;
    }

    public Double getLatePercentage() {
        return latePercentage;
    }

    public void setLatePercentage(Double latePercentage) {
        this.latePercentage = latePercentage;
    }
    
}
