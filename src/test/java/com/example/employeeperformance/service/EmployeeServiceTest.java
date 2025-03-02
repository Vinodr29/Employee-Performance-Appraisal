package com.example.employeeperformance.service;

import com.example.employeeperformance.entity.Employee;
import com.example.employeeperformance.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        employees = Arrays.asList(
                new Employee("1", "Alice", "A"),
                new Employee("2", "Bob", "B"),
                new Employee("3", "Charlie", "C"),
                new Employee("4", "David", "C"),
                new Employee("5", "Eve", "D"),
                new Employee("6", "Frank", "E")
        );
    }

    @Test
    void testCalculateActualPercentage() {
        when(employeeRepository.findAll()).thenReturn(employees);
        Map<String, Double> actualPercentage = employeeService.calculateActualPercentage();

        assertEquals(16.67, actualPercentage.get("A"), 0.1);
        assertEquals(16.67, actualPercentage.get("B"), 0.1);
        assertEquals(33.33, actualPercentage.get("C"), 0.1);
        assertEquals(16.67, actualPercentage.get("D"), 0.1);
        assertEquals(16.67, actualPercentage.get("E"), 0.1);
    }

    @Test
    void testCalculateDeviation() {
        when(employeeRepository.findAll()).thenReturn(employees);
        Map<String, Double> actualPercentage = employeeService.calculateActualPercentage();
        Map<String, Double> deviation = employeeService.calculateDeviation(actualPercentage);

        assertEquals(6.67, deviation.get("A"), 0.1);
        assertEquals(-3.33, deviation.get("B"), 0.1);
        assertEquals(-6.67, deviation.get("C"), 0.1);
        assertEquals(-3.33, deviation.get("D"), 0.1);
        assertEquals(6.67, deviation.get("E"), 0.1);
    }

    @Test
    void testSuggestRatingRevisions() {
        when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> revisedEmployees = employeeService.suggestRatingRevisions();

        assertNotNull(revisedEmployees);
        assertEquals(6, revisedEmployees.size());
        for (Employee employee : revisedEmployees) {
            assertNotNull(employee.getSuggestedRating());
        }
    }
}
