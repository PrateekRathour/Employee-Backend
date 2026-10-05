package com.emp.management.service.impl;

import com.emp.management.dto.DepartmentDto;
import com.emp.management.dto.DepartmentSearchCriteria;
import com.emp.management.entity.Department;
import com.emp.management.entity.Employee;
import com.emp.management.exception.DuplicateResourceException;
import com.emp.management.exception.ResourceNotFoundException;
import com.emp.management.repository.DepartmentRepository;
import com.emp.management.repository.EmployeeRepository;
import com.emp.management.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDto> searchDepartments(DepartmentSearchCriteria criteria) {
        String query = criteria != null && criteria.getQuery() != null && !criteria.getQuery().trim().isEmpty() 
                ? criteria.getQuery().trim() : null;
        String location = criteria != null && criteria.getLocation() != null && !criteria.getLocation().trim().isEmpty() 
                ? criteria.getLocation().trim() : null;

        return departmentRepository.searchDepartments(query, location).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDto getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
        return mapToDto(department);
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto dto) {
        if (departmentRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new DuplicateResourceException("Department", "name", dto.getName());
        }
        if (departmentRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new DuplicateResourceException("Department", "code", dto.getCode());
        }

        Department department = new Department();
        department.setName(dto.getName().trim());
        department.setCode(dto.getCode().trim().toUpperCase());
        department.setDescription(dto.getDescription());
        department.setLocation(dto.getLocation());
        department.setHeadOfDepartment(dto.getHeadOfDepartment());

        Department saved = departmentRepository.save(department);
        return mapToDto(saved);
    }

    @Override
    public DepartmentDto updateDepartment(Long id, DepartmentDto dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));

        if (!department.getName().equalsIgnoreCase(dto.getName()) && departmentRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new DuplicateResourceException("Department", "name", dto.getName());
        }
        if (!department.getCode().equalsIgnoreCase(dto.getCode()) && departmentRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new DuplicateResourceException("Department", "code", dto.getCode());
        }

        department.setName(dto.getName().trim());
        department.setCode(dto.getCode().trim().toUpperCase());
        department.setDescription(dto.getDescription());
        department.setLocation(dto.getLocation());
        department.setHeadOfDepartment(dto.getHeadOfDepartment());

        Department updated = departmentRepository.save(department);
        return mapToDto(updated);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
        
        long employeeCount = employeeRepository.countByDepartmentId(id);
        if (employeeCount > 0) {
            throw new IllegalArgumentException("Cannot delete department because it still has " + employeeCount + " assigned employee(s). Please reassign them first.");
        }
        departmentRepository.delete(department);
    }

    private DepartmentDto mapToDto(Department entity) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setDescription(entity.getDescription());
        dto.setLocation(entity.getLocation());
        dto.setHeadOfDepartment(entity.getHeadOfDepartment());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        List<Employee> emps = employeeRepository.findByDepartmentId(entity.getId());
        dto.setEmployeeCount((long) emps.size());
        
        if (!emps.isEmpty()) {
            double avgSalary = emps.stream()
                    .map(Employee::getSalary)
                    .filter(Objects::nonNull)
                    .mapToDouble(BigDecimal::doubleValue)
                    .average()
                    .orElse(0.0);
            dto.setAverageSalary(Math.round(avgSalary * 100.0) / 100.0);
        } else {
            dto.setAverageSalary(0.0);
        }

        return dto;
    }
}
