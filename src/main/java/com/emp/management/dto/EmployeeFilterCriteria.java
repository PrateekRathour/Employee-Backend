package com.emp.management.dto;

import com.emp.management.entity.EmploymentStatus;

public class EmployeeFilterCriteria {
    private String query;
    private Long departmentId;
    private EmploymentStatus status;
    private String designation;
    private String sortBy = "id";
    private String sortDir = "desc";
    private int page = 0;
    private int size = 10;

    public EmployeeFilterCriteria() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public EmploymentStatus getStatus() {
        return status;
    }

    public void setStatus(EmploymentStatus status) {
        this.status = status;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
