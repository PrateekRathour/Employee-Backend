package com.emp.management.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class DashboardStatsDto {
    private long totalEmployees;
    private long activeEmployees;
    private long onboardingEmployees;
    private long probationEmployees;
    private long onLeaveEmployees;
    private long totalDepartments;
    private BigDecimal totalPayroll;
    private BigDecimal averageSalary;
    private Map<String, Long> statusDistribution;
    private List<DepartmentBreakdown> departmentBreakdown;
    private List<EmployeeResponseDto> recentJoiners;

    public DashboardStatsDto() {
    }

    public static class DepartmentBreakdown {
        private Long departmentId;
        private String departmentName;
        private String departmentCode;
        private long employeeCount;
        private BigDecimal totalSalary;

        public DepartmentBreakdown() {
        }

        public DepartmentBreakdown(Long departmentId, String departmentName, String departmentCode, long employeeCount, BigDecimal totalSalary) {
            this.departmentId = departmentId;
            this.departmentName = departmentName;
            this.departmentCode = departmentCode;
            this.employeeCount = employeeCount;
            this.totalSalary = totalSalary;
        }

        public Long getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Long departmentId) {
            this.departmentId = departmentId;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getDepartmentCode() {
            return departmentCode;
        }

        public void setDepartmentCode(String departmentCode) {
            this.departmentCode = departmentCode;
        }

        public long getEmployeeCount() {
            return employeeCount;
        }

        public void setEmployeeCount(long employeeCount) {
            this.employeeCount = employeeCount;
        }

        public BigDecimal getTotalSalary() {
            return totalSalary;
        }

        public void setTotalSalary(BigDecimal totalSalary) {
            this.totalSalary = totalSalary;
        }
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getActiveEmployees() {
        return activeEmployees;
    }

    public void setActiveEmployees(long activeEmployees) {
        this.activeEmployees = activeEmployees;
    }

    public long getOnboardingEmployees() {
        return onboardingEmployees;
    }

    public void setOnboardingEmployees(long onboardingEmployees) {
        this.onboardingEmployees = onboardingEmployees;
    }

    public long getProbationEmployees() {
        return probationEmployees;
    }

    public void setProbationEmployees(long probationEmployees) {
        this.probationEmployees = probationEmployees;
    }

    public long getOnLeaveEmployees() {
        return onLeaveEmployees;
    }

    public void setOnLeaveEmployees(long onLeaveEmployees) {
        this.onLeaveEmployees = onLeaveEmployees;
    }

    public long getTotalDepartments() {
        return totalDepartments;
    }

    public void setTotalDepartments(long totalDepartments) {
        this.totalDepartments = totalDepartments;
    }

    public BigDecimal getTotalPayroll() {
        return totalPayroll;
    }

    public void setTotalPayroll(BigDecimal totalPayroll) {
        this.totalPayroll = totalPayroll;
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public Map<String, Long> getStatusDistribution() {
        return statusDistribution;
    }

    public void setStatusDistribution(Map<String, Long> statusDistribution) {
        this.statusDistribution = statusDistribution;
    }

    public List<DepartmentBreakdown> getDepartmentBreakdown() {
        return departmentBreakdown;
    }

    public void setDepartmentBreakdown(List<DepartmentBreakdown> departmentBreakdown) {
        this.departmentBreakdown = departmentBreakdown;
    }

    public List<EmployeeResponseDto> getRecentJoiners() {
        return recentJoiners;
    }

    public void setRecentJoiners(List<EmployeeResponseDto> recentJoiners) {
        this.recentJoiners = recentJoiners;
    }
}
