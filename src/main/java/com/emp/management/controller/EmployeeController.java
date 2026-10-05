package com.emp.management.controller;

import com.emp.management.dto.ApiResponse;
import com.emp.management.dto.EmployeeFilterCriteria;
import com.emp.management.dto.EmployeeRequestDto;
import com.emp.management.dto.EmployeeResponseDto;
import com.emp.management.entity.EmploymentStatus;
import com.emp.management.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "REST APIs for employee onboarding, profile management, and lifecycle")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/onboard")
    @Operation(summary = "Onboard a new employee", description = "Validates input, assigns unique employee code, and registers employee")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> onboardEmployee(@Valid @RequestBody EmployeeRequestDto request) {
        EmployeeResponseDto response = employeeService.onboardEmployee(request);
        return new ResponseEntity<>(ApiResponse.created("Employee onboarded successfully with code " + response.getEmployeeCode(), response), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Search and filter employees", description = "Supports query, department, status filter, pagination and sorting")
    public ResponseEntity<ApiResponse<Page<EmployeeResponseDto>>> searchAndFilterEmployees(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) EmploymentStatus status,
            @RequestParam(required = false) String designation,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        EmployeeFilterCriteria criteria = new EmployeeFilterCriteria();
        criteria.setQuery(query);
        criteria.setDepartmentId(departmentId);
        criteria.setStatus(status);
        criteria.setDesignation(designation);
        criteria.setSortBy(sortBy);
        criteria.setSortDir(sortDir);
        criteria.setPage(page);
        criteria.setSize(size);

        Page<EmployeeResponseDto> employeePage = employeeService.searchAndFilterEmployees(criteria);
        return ResponseEntity.ok(ApiResponse.ok("Employees fetched successfully", employeePage));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee profile by ID", description = "Fetches complete profile details of an employee")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> getEmployeeById(@PathVariable Long id) {
        EmployeeResponseDto response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(ApiResponse.ok("Employee profile retrieved", response));
    }

    @GetMapping("/code/{employeeCode}")
    @Operation(summary = "Get employee profile by Employee Code")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> getEmployeeByCode(@PathVariable String employeeCode) {
        EmployeeResponseDto response = employeeService.getEmployeeByCode(employeeCode);
        return ResponseEntity.ok(ApiResponse.ok("Employee profile retrieved", response));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee profile", description = "Updates profile details with full validation")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployeeProfile(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto request) {
        EmployeeResponseDto response = employeeService.updateEmployeeProfile(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Employee profile updated successfully", response));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update employee employment status", description = "Changes status to ACTIVE, ONBOARDING, PROBATION, ON_LEAVE, or TERMINATED")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployeeStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusPayload) {
        String statusStr = statusPayload.get("status");
        if (statusStr == null || statusStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Status field is required");
        }
        EmploymentStatus status = EmploymentStatus.valueOf(statusStr.toUpperCase());
        EmployeeResponseDto response = employeeService.updateEmployeeStatus(id, status);
        return ResponseEntity.ok(ApiResponse.ok("Employee status updated to " + status, response));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an employee record", description = "Permanently removes an employee from the system")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponse.ok("Employee with ID " + id + " deleted successfully", null));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get all employees in a specific department")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        List<EmployeeResponseDto> list = employeeService.getEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.ok("Department employees retrieved", list));
    }
}
