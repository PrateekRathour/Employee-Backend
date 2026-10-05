package com.emp.management.service.impl;

import com.emp.management.dto.EmployeeFilterCriteria;
import com.emp.management.dto.EmployeeRequestDto;
import com.emp.management.dto.EmployeeResponseDto;
import com.emp.management.entity.Department;
import com.emp.management.entity.Employee;
import com.emp.management.entity.EmploymentStatus;
import com.emp.management.exception.DuplicateResourceException;
import com.emp.management.exception.ResourceNotFoundException;
import com.emp.management.repository.DepartmentRepository;
import com.emp.management.repository.EmployeeRepository;
import com.emp.management.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public EmployeeResponseDto onboardEmployee(EmployeeRequestDto request) {
        if (employeeRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("Employee", "email", request.getEmail());
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.getDepartmentId()));

        Employee employee = new Employee();
        employee.setEmployeeCode(generateUniqueEmployeeCode(department.getCode()));
        employee.setFirstName(request.getFirstName().trim());
        employee.setLastName(request.getLastName().trim());
        employee.setEmail(request.getEmail().trim().toLowerCase());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation().trim());
        employee.setDepartment(department);
        employee.setStatus(request.getStatus() != null ? request.getStatus() : EmploymentStatus.ONBOARDING);
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setAvatarUrl(request.getAvatarUrl() != null && !request.getAvatarUrl().isBlank() 
                ? request.getAvatarUrl() 
                : generateDefaultAvatar(request.getFirstName(), request.getLastName()));
        employee.setAddress(request.getAddress());
        employee.setEmergencyContact(request.getEmergencyContact());
        employee.setSkills(request.getSkills() != null ? new ArrayList<>(request.getSkills()) : new ArrayList<>());

        Employee saved = employeeRepository.save(employee);
        return mapToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        return mapToDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByCode(String employeeCode) {
        Employee employee = employeeRepository.findByEmployeeCodeIgnoreCase(employeeCode)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "employeeCode", employeeCode));
        return mapToDto(employee);
    }

    @Override
    public EmployeeResponseDto updateEmployeeProfile(Long id, EmployeeRequestDto request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));

        // Check email uniqueness if modified
        if (!employee.getEmail().equalsIgnoreCase(request.getEmail()) 
                && employeeRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateResourceException("Employee", "email", request.getEmail());
        }

        // Verify department
        if (!employee.getDepartment().getId().equals(request.getDepartmentId())) {
            Department newDept = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department", "id", request.getDepartmentId()));
            employee.setDepartment(newDept);
        }

        employee.setFirstName(request.getFirstName().trim());
        employee.setLastName(request.getLastName().trim());
        employee.setEmail(request.getEmail().trim().toLowerCase());
        employee.setPhone(request.getPhone());
        employee.setDesignation(request.getDesignation().trim());
        if (request.getStatus() != null) {
            employee.setStatus(request.getStatus());
        }
        employee.setSalary(request.getSalary());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setDateOfBirth(request.getDateOfBirth());
        if (request.getAvatarUrl() != null && !request.getAvatarUrl().isBlank()) {
            employee.setAvatarUrl(request.getAvatarUrl());
        }
        employee.setAddress(request.getAddress());
        employee.setEmergencyContact(request.getEmergencyContact());
        if (request.getSkills() != null) {
            employee.setSkills(new ArrayList<>(request.getSkills()));
        }

        Employee updated = employeeRepository.save(employee);
        return mapToDto(updated);
    }

    @Override
    public EmployeeResponseDto updateEmployeeStatus(Long id, EmploymentStatus status) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employee.setStatus(status);
        Employee updated = employeeRepository.save(employee);
        return mapToDto(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> searchAndFilterEmployees(EmployeeFilterCriteria criteria) {
        String sortBy = criteria.getSortBy() != null ? criteria.getSortBy() : "id";
        Sort.Direction direction = "asc".equalsIgnoreCase(criteria.getSortDir()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), Sort.by(direction, sortBy));

        String query = (criteria.getQuery() != null && !criteria.getQuery().trim().isEmpty()) 
                ? criteria.getQuery().trim() : null;
        String designation = (criteria.getDesignation() != null && !criteria.getDesignation().trim().isEmpty()) 
                ? criteria.getDesignation().trim() : null;

        Page<Employee> employeePage = employeeRepository.filterEmployees(
                query,
                criteria.getDepartmentId(),
                criteria.getStatus(),
                designation,
                pageable
        );

        return employeePage.map(this::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getEmployeesByDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Department", "id", departmentId);
        }
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private String generateUniqueEmployeeCode(String deptCode) {
        String prefix = (deptCode != null && !deptCode.isBlank()) ? deptCode.toUpperCase() : "EMP";
        for (int i = 0; i < 20; i++) {
            int randomNum = 1000 + new Random().nextInt(9000);
            String candidateCode = "EMP-" + prefix + "-" + randomNum;
            if (!employeeRepository.existsByEmployeeCodeIgnoreCase(candidateCode)) {
                return candidateCode;
            }
        }
        return "EMP-" + System.currentTimeMillis() % 100000;
    }

    private String generateDefaultAvatar(String firstName, String lastName) {
        String initials = "";
        if (firstName != null && !firstName.isEmpty()) initials += firstName.charAt(0);
        if (lastName != null && !lastName.isEmpty()) initials += lastName.charAt(0);
        return "https://api.dicebear.com/7.x/initials/svg?seed=" + initials.toUpperCase() + "&backgroundColor=1976d2,4338ca,0d9488";
    }

    private EmployeeResponseDto mapToDto(Employee entity) {
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(entity.getId());
        dto.setEmployeeCode(entity.getEmployeeCode());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setFullName(entity.getFullName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setDesignation(entity.getDesignation());
        if (entity.getDepartment() != null) {
            dto.setDepartmentId(entity.getDepartment().getId());
            dto.setDepartmentName(entity.getDepartment().getName());
            dto.setDepartmentCode(entity.getDepartment().getCode());
        }
        dto.setStatus(entity.getStatus());
        dto.setSalary(entity.getSalary());
        dto.setJoiningDate(entity.getJoiningDate());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setAvatarUrl(entity.getAvatarUrl());
        dto.setAddress(entity.getAddress());
        dto.setEmergencyContact(entity.getEmergencyContact());
        dto.setSkills(entity.getSkills() != null ? new ArrayList<>(entity.getSkills()) : new ArrayList<>());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
