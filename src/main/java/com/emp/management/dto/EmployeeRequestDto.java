package com.emp.management.dto;

import com.emp.management.entity.EmploymentStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EmployeeRequestDto {

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email address is mandatory")
    @Email(message = "Please provide a valid corporate email address")
    private String email;

    @Pattern(regexp = "^$|^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$", message = "Please provide a valid phone number")
    private String phone;

    @NotBlank(message = "Designation/Job title is mandatory")
    private String designation;

    @NotNull(message = "Department ID is mandatory")
    private Long departmentId;

    private EmploymentStatus status;

    @NotNull(message = "Salary is mandatory")
    @DecimalMin(value = "1000.00", message = "Salary must be at least 1,000.00")
    @Digits(integer = 10, fraction = 2, message = "Salary format is invalid")
    private BigDecimal salary;

    @NotNull(message = "Joining date is mandatory")
    private LocalDate joiningDate;

    private LocalDate dateOfBirth;

    private String avatarUrl;

    private String address;

    private String emergencyContact;

    private List<String> skills;

    public EmployeeRequestDto() {
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
}
