package com.example.sms.DTO;

public class GradeSubjectRequest {
    private Integer gradeId;
    private Integer subjectId;
    private Integer teacherId;
    public Integer getGradeId() {
        return gradeId;
    }
    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }
    public Integer getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }
    public Integer getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    
}
