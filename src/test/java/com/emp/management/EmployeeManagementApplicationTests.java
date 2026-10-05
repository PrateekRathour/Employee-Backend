package com.emp.management;

import com.emp.management.dto.EmployeeRequestDto;
import com.emp.management.dto.EmployeeResponseDto;
import com.emp.management.entity.Department;
import com.emp.management.entity.EmploymentStatus;
import com.emp.management.repository.DepartmentRepository;
import com.emp.management.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
class EmployeeManagementApplicationTests {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Long engineeringDeptId;

    @BeforeEach
    void setUp() {
        Department dept = departmentRepository.findAll().stream().findFirst().orElseGet(() -> {
            Department d = new Department(null, "Test Engineering", "TENG", "Test Dept", "Bldg 1", "Lead Engineer");
            return departmentRepository.save(d);
        });
        engineeringDeptId = dept.getId();
    }

    @Test
    @DisplayName("Test Onboarding a new employee with valid payload")
    void testOnboardEmployee() {
        EmployeeRequestDto request = new EmployeeRequestDto();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe.test@company.com");
        request.setPhone("+1 555-4321");
        request.setDesignation("Software Engineer");
        request.setDepartmentId(engineeringDeptId);
        request.setStatus(EmploymentStatus.ONBOARDING);
        request.setSalary(new BigDecimal("95000.00"));
        request.setJoiningDate(LocalDate.now());
        request.setSkills(List.of("Java", "Spring Boot", "Docker"));

        EmployeeResponseDto response = employeeService.onboardEmployee(request);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getEmployeeCode());
        assertEquals("John", response.getFirstName());
        assertEquals("Doe", response.getLastName());
        assertEquals("john.doe.test@company.com", response.getEmail());
        assertEquals(EmploymentStatus.ONBOARDING, response.getStatus());
        assertTrue(response.getSkills().contains("Java"));
    }

    @Test
    @DisplayName("Test Employee Status Transition")
    void testUpdateStatus() {
        EmployeeRequestDto request = new EmployeeRequestDto();
        request.setFirstName("Jane");
        request.setLastName("Smith");
        request.setEmail("jane.smith.test@company.com");
        request.setPhone("+1 555-8765");
        request.setDesignation("Senior QA Engineer");
        request.setDepartmentId(engineeringDeptId);
        request.setStatus(EmploymentStatus.ONBOARDING);
        request.setSalary(new BigDecimal("90000.00"));
        request.setJoiningDate(LocalDate.now());

        EmployeeResponseDto created = employeeService.onboardEmployee(request);
        EmployeeResponseDto updated = employeeService.updateEmployeeStatus(created.getId(), EmploymentStatus.ACTIVE);

        assertEquals(EmploymentStatus.ACTIVE, updated.getStatus());
    }
}
