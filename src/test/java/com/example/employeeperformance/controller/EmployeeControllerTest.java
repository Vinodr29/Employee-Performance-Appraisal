package com.example.employeeperformance.controller;

import com.example.employeeperformance.entity.Employee;
import com.example.employeeperformance.repository.EmployeeRepository;
import com.example.employeeperformance.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Model model;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testShowAddEmployeeForm() {
        String viewName = employeeController.showAddEmployeeForm();
        assertEquals("add-employee", viewName);
    }

    @Test
    void testAddEmployee() {
        String viewName = employeeController.addEmployee("1", "John", "A");
        assertEquals("redirect:/employees", viewName);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testListEmployees() {
        // Mock data
        List<Employee> employees = Arrays.asList(
                new Employee("1", "John", "A"),
                new Employee("2", "Jane", "B")
        );

        // Mock repository behavior
        when(employeeRepository.findAll()).thenReturn(employees);

        // Call the method
        String viewName = employeeController.listEmployees(model);

        // Verify the result
        assertEquals("employee-list", viewName);
        verify(model, times(1)).addAttribute("employees", employees);
    }

    @Test
    void testShowBellCurve() {
        // Mock data
        Map<String, Double> actualPercentage = new HashMap<>();
        actualPercentage.put("A", 15.0);
        actualPercentage.put("B", 25.0);

        Map<String, Double> deviation = new HashMap<>();
        deviation.put("A", 5.0);
        deviation.put("B", 5.0);

        // Mock service behavior
        when(employeeService.calculateActualPercentage()).thenReturn(actualPercentage);
        when(employeeService.calculateDeviation(actualPercentage)).thenReturn(deviation);

        // Call the method
        String viewName = employeeController.showBellCurve(model);

        // Verify the result
        assertEquals("bell-curve", viewName);
        verify(model, times(1)).addAttribute("actualPercentage", actualPercentage);
        verify(model, times(1)).addAttribute("standardDistribution", EmployeeService.STANDARD_DISTRIBUTION);
        verify(model, times(1)).addAttribute("deviation", deviation);
    }

    @Test
    void testSuggestRatingRevisions() {
        // Mock data
        List<Employee> revisedEmployees = Arrays.asList(
                new Employee("1", "John", "B"),
                new Employee("2", "Jane", "C")
        );

        // Mock service behavior
        when(employeeService.suggestRatingRevisions()).thenReturn(revisedEmployees);

        // Call the method
        String viewName = employeeController.suggestRatingRevisions(model);

        // Verify the result
        assertEquals("suggest-revisions", viewName);
        verify(model, times(1)).addAttribute("revisedEmployees", revisedEmployees);
    }
}