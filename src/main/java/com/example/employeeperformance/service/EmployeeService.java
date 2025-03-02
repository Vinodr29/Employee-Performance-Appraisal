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

    // Standard bell curve distribution
    public static final Map<String, Double> STANDARD_DISTRIBUTION = Map.of(
            "A", 10.0,
            "B", 20.0,
            "C", 40.0,
            "D", 20.0,
            "E", 10.0
    );

    // Calculate actual percentage of employees in each rating category
    public Map<String, Double> calculateActualPercentage() {
        List<Employee> employees = employeeRepository.findAll();
        int totalEmployees = employees.size();
        Map<String, Double> actualPercentage = new HashMap<>();

        // Initialize counts for each rating category
        Map<String, Integer> ratingCount = new HashMap<>();
        for (String rating : STANDARD_DISTRIBUTION.keySet()) {
            ratingCount.put(rating, 0);
        }

        // Count employees in each rating category
        for (Employee employee : employees) {
            ratingCount.put(employee.getRating(), ratingCount.getOrDefault(employee.getRating(), 0) + 1);
        }

        // Calculate actual percentage for each category
        for (String rating : STANDARD_DISTRIBUTION.keySet()) {
            actualPercentage.put(rating, (ratingCount.get(rating) * 100.0) / totalEmployees);
        }

        return actualPercentage;
    }

    // Calculate deviation between actual and standard distribution
    public Map<String, Double> calculateDeviation(Map<String, Double> actualPercentage) {
        Map<String, Double> deviation = new HashMap<>();
        for (String rating : STANDARD_DISTRIBUTION.keySet()) {
            double actual = actualPercentage.getOrDefault(rating, 0.0);
            double standard = STANDARD_DISTRIBUTION.get(rating);
            deviation.put(rating, actual - standard);
        }
        return deviation;
    }

    // Suggest rating revisions to align with the bell curve
    public List<Employee> suggestRatingRevisions() {
        Map<String, Double> actualPercentage = calculateActualPercentage();
        Map<String, Double> deviation = calculateDeviation(actualPercentage);
        List<Employee> employees = employeeRepository.findAll();
        List<Employee> revisedEmployees = new ArrayList<>();

        for (Employee employee : employees) {
            String currentRating = employee.getRating();
            double currentDeviation = deviation.getOrDefault(currentRating, 0.0);
            String suggestedRating = currentRating; // Default to current rating

            // If category is overrepresented, try to move employees to underrepresented ones
            if (currentDeviation > 0) {
                switch (currentRating) {
                    case "A":
                        suggestedRating = "B";
                        break;
                    case "B":
                        suggestedRating = "C";
                        break;
                    case "C":
                        if (deviation.getOrDefault("B", 0.0) < 0) {
                            suggestedRating = "B";
                        } else if (deviation.getOrDefault("D", 0.0) < 0) {
                            suggestedRating = "D";
                        }
                        break;
                    case "D":
                        suggestedRating = "C";
                        break;
                    case "E":
                        suggestedRating = "D";
                        break;
                    default:
                        // No change for unknown ratings
                        break;
                }
            }

            // Create a revised employee object with the suggested rating
            Employee revisedEmployee = new Employee(
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                currentRating
            );
            revisedEmployee.setSuggestedRating(suggestedRating);
            revisedEmployees.add(revisedEmployee);
        }

        return revisedEmployees;
    }
}
