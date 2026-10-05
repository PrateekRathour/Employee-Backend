package com.emp.management.controller;

import com.emp.management.dto.ApiResponse;
import com.emp.management.dto.DepartmentDto;
import com.emp.management.dto.DepartmentSearchCriteria;
import com.emp.management.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "Department Management", description = "REST APIs for department search, creation, and updates")
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    @Operation(summary = "Search and list departments", description = "Search departments by name/code/description query and location")
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> searchDepartments(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String location) {
        DepartmentSearchCriteria criteria = new DepartmentSearchCriteria(query, location);
        List<DepartmentDto> departments = departmentService.searchDepartments(criteria);
        return ResponseEntity.ok(ApiResponse.ok("Departments retrieved successfully", departments));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID")
    public ResponseEntity<ApiResponse<DepartmentDto>> getDepartmentById(@PathVariable Long id) {
        DepartmentDto department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(ApiResponse.ok("Department retrieved", department));
    }

    @PostMapping
    @Operation(summary = "Create a new department")
    public ResponseEntity<ApiResponse<DepartmentDto>> createDepartment(@Valid @RequestBody DepartmentDto dto) {
        DepartmentDto created = departmentService.createDepartment(dto);
        return new ResponseEntity<>(ApiResponse.created("Department created successfully", created), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing department")
    public ResponseEntity<ApiResponse<DepartmentDto>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDto dto) {
        DepartmentDto updated = departmentService.updateDepartment(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Department updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a department")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok(ApiResponse.ok("Department deleted successfully", null));
    }
}
