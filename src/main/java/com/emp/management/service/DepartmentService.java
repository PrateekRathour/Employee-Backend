package com.emp.management.service;

import com.emp.management.dto.DepartmentDto;
import com.emp.management.dto.DepartmentSearchCriteria;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> getAllDepartments();
    List<DepartmentDto> searchDepartments(DepartmentSearchCriteria criteria);
    DepartmentDto getDepartmentById(Long id);
    DepartmentDto createDepartment(DepartmentDto departmentDto);
    DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto);
    void deleteDepartment(Long id);
}
