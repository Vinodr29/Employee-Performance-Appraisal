package com.example.employeeperformance.entity;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employeeId;
    private String employeeName;
    private String rating;

    @Transient 
    private String suggestedRating;

    
    public Employee() {}

   
    public Employee(String employeeId, String employeeName, String rating) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.rating = rating;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSuggestedRating() {
        return suggestedRating;
    }

    public void setSuggestedRating(String suggestedRating) {
        this.suggestedRating = suggestedRating;
    }
}