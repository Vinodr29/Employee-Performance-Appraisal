package com.example.employeeperformance.service;

import com.example.employeeperformance.entity.Employee;
import com.example.employeeperformance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    
    public static final Map<String, Double> STANDARD_DISTRIBUTION = Map.of(
            "A", 10.0,
            "B", 20.0,
            "C", 40.0,
            "D", 20.0,
            "E", 10.0
    );

    
    public Map<String, Double> calculateActualPercentage() {
        List<Employee> employees = employeeRepository.findAll();
        int totalEmployees = employees.size();
        Map<String, Double> actualPercentage = new HashMap<>();

        
        Map<String, Integer> ratingCount = new HashMap<>();
        for (String rating : STANDARD_DISTRIBUTION.keySet()) {
            ratingCount.put(rating, 0);
        }

        
        for (Employee employee : employees) {
            ratingCount.put(employee.getRating(), ratingCount.getOrDefault(employee.getRating(), 0) + 1);
        }

        
        for (String rating : STANDARD_DISTRIBUTION.keySet()) {
            actualPercentage.put(rating, (ratingCount.get(rating) * 100.0) / totalEmployees);
        }

        return actualPercentage;
    }

    
    public Map<String, Double> calculateDeviation(Map<String, Double> actualPercentage) {
        Map<String, Double> deviation = new HashMap<>();
        for (String rating : STANDARD_DISTRIBUTION.keySet()) {
            double actual = actualPercentage.getOrDefault(rating, 0.0);
            double standard = STANDARD_DISTRIBUTION.get(rating);
            deviation.put(rating, actual - standard);
        }
        return deviation;
    }

    
  
    public List<Employee> suggestRatingRevisions() {
        Map<String, Double> actualPercentage = calculateActualPercentage();
        Map<String, Double> deviation = calculateDeviation(actualPercentage);
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> revisedEmployees = new ArrayList<>();

        Map<String, List<Employee>> ratingGroups = new HashMap<>();
        for (Employee employee : employees) {
            String rating = employee.getRating();
            if (!ratingGroups.containsKey(rating)) {
                ratingGroups.put(rating, new ArrayList<>());
            }
            ratingGroups.get(rating).add(employee);
        }

        Map<String, Integer> moveDownCount = new HashMap<>();
        Map<String, Integer> moveUpCount = new HashMap<>();

        for (Map.Entry<String, Double> entry : deviation.entrySet()) {
            String rating = entry.getKey();
            double dev = entry.getValue();
            int count = (int) Math.ceil((Math.abs(dev) / 100) * employees.size());

            if (ratingGroups.containsKey(rating) && ratingGroups.get(rating).size() >= 4) {
                if (dev > 0) {
                    moveDownCount.put(rating, count);
                } else if (dev < 0) {
                    moveUpCount.put(rating, count);
                }
            }
        }

        for (Employee employee : employees) {
            String currentRating = employee.getRating();
            String suggestedRating = currentRating;

            if (moveDownCount.containsKey(currentRating) && moveDownCount.get(currentRating) > 0) {
                suggestedRating = getLowerRating(currentRating);
                moveDownCount.put(currentRating, moveDownCount.get(currentRating) - 1);
            } else if (moveUpCount.containsKey(currentRating) && moveUpCount.get(currentRating) > 0) {
                suggestedRating = getHigherRating(currentRating);
                moveUpCount.put(currentRating, moveUpCount.get(currentRating) - 1);
            }

            Employee revisedEmployee = new Employee(
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getRating()
            );
            revisedEmployee.setSuggestedRating(suggestedRating);
            revisedEmployees.add(revisedEmployee);
        }

        return revisedEmployees;
    }

    private String getLowerRating(String rating) {
        if (rating.equals("A")) return "B";
        if (rating.equals("B")) return "C";
        if (rating.equals("C")) return "D";
        if (rating.equals("D")) return "E";
        return rating;
    }

    private String getHigherRating(String rating) {
        if (rating.equals("E")) return "D";
        if (rating.equals("D")) return "C";
        if (rating.equals("C")) return "B";
        if (rating.equals("B")) return "A";
        return rating;
    }



}



