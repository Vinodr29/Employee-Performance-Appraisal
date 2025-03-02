package com.example.employeeperformance.controller;

import com.example.employeeperformance.entity.Employee;
import com.example.employeeperformance.repository.EmployeeRepository;
import com.example.employeeperformance.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    // Display the form to add an employee
    @GetMapping("/add-form")
    public String showAddEmployeeForm() {
        return "add-employee"; // Refers to add-employee.html
    }

    // Handle form submission
    @PostMapping("/add")
    public String addEmployee(@RequestParam String employeeId, @RequestParam String employeeName, @RequestParam String rating) {
        Employee employee = new Employee(employeeId, employeeName, rating);
        employeeRepository.save(employee);
        return "redirect:/employees";
    }

    // Display all employees
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee-list"; // Refers to employee-list.html
    }

    // Display bell curve analysis
    @GetMapping("/bell-curve")
    public String showBellCurve(Model model) {
        Map<String, Double> actualPercentage = employeeService.calculateActualPercentage();
        Map<String, Double> deviation = employeeService.calculateDeviation(actualPercentage);

        model.addAttribute("actualPercentage", actualPercentage);
        model.addAttribute("standardDistribution", EmployeeService.STANDARD_DISTRIBUTION);
        model.addAttribute("deviation", deviation);

        return "bell-curve"; // Refers to bell-curve.html
    }

    // Display suggested rating revisions
    @GetMapping("/suggest-revisions")
    public String suggestRatingRevisions(Model model) {
        model.addAttribute("revisedEmployees", employeeService.suggestRatingRevisions());
        return "suggest-revisions"; // Refers to suggest-revisions.html
    }
}