package com.emp.management.service;

import com.emp.management.dto.EmployeeFilterCriteria;
import com.emp.management.dto.EmployeeRequestDto;
import com.emp.management.dto.EmployeeResponseDto;
import com.emp.management.entity.EmploymentStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDto onboardEmployee(EmployeeRequestDto request);
    EmployeeResponseDto getEmployeeById(Long id);
    EmployeeResponseDto getEmployeeByCode(String employeeCode);
    EmployeeResponseDto updateEmployeeProfile(Long id, EmployeeRequestDto request);
    EmployeeResponseDto updateEmployeeStatus(Long id, EmploymentStatus status);
    void deleteEmployee(Long id);
    Page<EmployeeResponseDto> searchAndFilterEmployees(EmployeeFilterCriteria criteria);
    List<EmployeeResponseDto> getEmployeesByDepartment(Long departmentId);
}
