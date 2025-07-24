package com.example.sms.Models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

public class Materials {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Integer materialId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "material")
    private byte[] material;

    @JoinColumn(name = "grade_subject_id")
    private GradeSubject gradeSubject;

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getMaterial() {
        return material;
    }

    public void setMaterial(byte[] material) {
        this.material = material;
    }

    public GradeSubject getGradeSubject() {
        return gradeSubject;
    }

    public void setGradeSubject(GradeSubject gradeSubject) {
        this.gradeSubject = gradeSubject;
    }

    


}
