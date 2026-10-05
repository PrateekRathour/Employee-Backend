package com.emp.management.service.impl;

import com.emp.management.dto.DashboardStatsDto;
import com.emp.management.dto.EmployeeResponseDto;
import com.emp.management.entity.Department;
import com.emp.management.entity.Employee;
import com.emp.management.entity.EmploymentStatus;
import com.emp.management.repository.DepartmentRepository;
import com.emp.management.repository.EmployeeRepository;
import com.emp.management.service.DashboardService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public DashboardServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DashboardStatsDto getDashboardStatistics() {
        DashboardStatsDto stats = new DashboardStatsDto();

        List<Employee> allEmployees = employeeRepository.findAll();
        List<Department> allDepartments = departmentRepository.findAll();

        stats.setTotalEmployees(allEmployees.size());
        stats.setTotalDepartments(allDepartments.size());

        // Status counts
        long active = allEmployees.stream().filter(e -> e.getStatus() == EmploymentStatus.ACTIVE).count();
        long onboarding = allEmployees.stream().filter(e -> e.getStatus() == EmploymentStatus.ONBOARDING).count();
        long probation = allEmployees.stream().filter(e -> e.getStatus() == EmploymentStatus.PROBATION).count();
        long onLeave = allEmployees.stream().filter(e -> e.getStatus() == EmploymentStatus.ON_LEAVE).count();

        stats.setActiveEmployees(active);
        stats.setOnboardingEmployees(onboarding);
        stats.setProbationEmployees(probation);
        stats.setOnLeaveEmployees(onLeave);

        Map<String, Long> statusDistribution = new LinkedHashMap<>();
        for (EmploymentStatus status : EmploymentStatus.values()) {
            long count = allEmployees.stream().filter(e -> e.getStatus() == status).count();
            statusDistribution.put(status.name(), count);
        }
        stats.setStatusDistribution(statusDistribution);

        // Salary metrics
        BigDecimal totalPayroll = allEmployees.stream()
                .map(Employee::getSalary)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setTotalPayroll(totalPayroll);

        if (!allEmployees.isEmpty() && totalPayroll.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal avg = totalPayroll.divide(BigDecimal.valueOf(allEmployees.size()), 2, RoundingMode.HALF_UP);
            stats.setAverageSalary(avg);
        } else {
            stats.setAverageSalary(BigDecimal.ZERO);
        }

        // Department breakdown
        List<DashboardStatsDto.DepartmentBreakdown> deptBreakdown = new ArrayList<>();
        for (Department dept : allDepartments) {
            List<Employee> deptEmps = allEmployees.stream()
                    .filter(e -> e.getDepartment() != null && e.getDepartment().getId().equals(dept.getId()))
                    .collect(Collectors.toList());

            BigDecimal deptSalary = deptEmps.stream()
                    .map(Employee::getSalary)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            deptBreakdown.add(new DashboardStatsDto.DepartmentBreakdown(
                    dept.getId(),
                    dept.getName(),
                    dept.getCode(),
                    deptEmps.size(),
                    deptSalary
            ));
        }
        stats.setDepartmentBreakdown(deptBreakdown);

        // Recent joiners (top 5)
        List<Employee> recent = employeeRepository.findRecentJoiners(PageRequest.of(0, 5));
        List<EmployeeResponseDto> recentDto = recent.stream().map(e -> {
            EmployeeResponseDto dto = new EmployeeResponseDto();
            dto.setId(e.getId());
            dto.setEmployeeCode(e.getEmployeeCode());
            dto.setFirstName(e.getFirstName());
            dto.setLastName(e.getLastName());
            dto.setFullName(e.getFullName());
            dto.setEmail(e.getEmail());
            dto.setDesignation(e.getDesignation());
            if (e.getDepartment() != null) {
                dto.setDepartmentId(e.getDepartment().getId());
                dto.setDepartmentName(e.getDepartment().getName());
                dto.setDepartmentCode(e.getDepartment().getCode());
            }
            dto.setStatus(e.getStatus());
            dto.setSalary(e.getSalary());
            dto.setJoiningDate(e.getJoiningDate());
            dto.setAvatarUrl(e.getAvatarUrl());
            return dto;
        }).collect(Collectors.toList());

        stats.setRecentJoiners(recentDto);

        return stats;
    }
}
