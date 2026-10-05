package com.emp.management.config;

import com.emp.management.entity.Department;
import com.emp.management.entity.Employee;
import com.emp.management.entity.EmploymentStatus;
import com.emp.management.repository.DepartmentRepository;
import com.emp.management.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DataInitializer(DepartmentRepository departmentRepository, EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
        if (departmentRepository.count() > 0) {
            return;
        }

        // 1. Seed Departments
        Department engineering = new Department(null, "Engineering & Technology", "ENG", 
                "Core software architecture, cloud platforms, devops, and application development", "Building A - Floor 4", "Dr. Alexander Wright");
        
        Department humanResources = new Department(null, "Human Resources", "HR", 
                "Talent acquisition, employee relations, learning & development, and culture", "Building B - Floor 2", "Elena Vance");
        
        Department productDesign = new Department(null, "Product & UI/UX Design", "DES", 
                "Product strategy, UX research, wireframing, and interactive design systems", "Building A - Floor 3", "Marcus Chen");
        
        Department marketing = new Department(null, "Global Marketing & Growth", "MKT", 
                "Brand management, digital marketing, PR, SEO, and content creation", "Building C - Floor 1", "Sophia Martinez");
        
        Department finance = new Department(null, "Finance & Operations", "FIN", 
                "Financial reporting, compliance, treasury, payroll, and business operations", "Building B - Floor 5", "David Sterling");

        departmentRepository.saveAll(Arrays.asList(engineering, humanResources, productDesign, marketing, finance));

        // 2. Seed Employees
        List<Employee> employees = Arrays.asList(
                createEmployee("EMP-ENG-1001", "Prateek", "Sharma", "prateek.sharma@example.com", "+1 555-0192", 
                        "Lead DevOps & Cloud Architect", engineering, EmploymentStatus.ACTIVE, new BigDecimal("135000.00"), 
                        LocalDate.of(2023, 1, 15), LocalDate.of(1994, 6, 20), 
                        "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&auto=format&fit=crop&q=80",
                        "742 Evergreen Terrace, Seattle, WA", "Dr. Alexander Wright (+1 555-9001)", 
                        Arrays.asList("Kubernetes", "AWS", "Docker", "Terraform", "CI/CD", "Java", "Spring Boot")),

                createEmployee("EMP-ENG-1002", "Sarah", "Jenkins", "sarah.jenkins@example.com", "+1 555-0144", 
                        "Senior Full Stack Engineer", engineering, EmploymentStatus.ACTIVE, new BigDecimal("120000.00"), 
                        LocalDate.of(2023, 4, 1), LocalDate.of(1996, 3, 14), 
                        "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&auto=format&fit=crop&q=80",
                        "1204 Pine St, San Francisco, CA", "Mark Jenkins (+1 555-9002)", 
                        Arrays.asList("Angular", "TypeScript", "Spring Boot", "MySQL", "RxJS", "Material UI")),

                createEmployee("EMP-ENG-1003", "Aarav", "Patel", "aarav.patel@example.com", "+1 555-0188", 
                        "Cloud Platform Engineer", engineering, EmploymentStatus.ONBOARDING, new BigDecimal("98000.00"), 
                        LocalDate.of(2025, 9, 10), LocalDate.of(1998, 11, 28), 
                        "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&auto=format&fit=crop&q=80",
                        "89 Silicon Ave, Austin, TX", "Priya Patel (+1 555-9003)", 
                        Arrays.asList("AWS", "Jenkins", "Linux", "Docker", "Python", "Prometheus")),

                createEmployee("EMP-HR-2001", "Elena", "Vance", "elena.vance@example.com", "+1 555-0133", 
                        "VP of People & Culture", humanResources, EmploymentStatus.ACTIVE, new BigDecimal("145000.00"), 
                        LocalDate.of(2022, 5, 20), LocalDate.of(1988, 8, 9), 
                        "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=150&auto=format&fit=crop&q=80",
                        "330 Park Avenue, New York, NY", "Thomas Vance (+1 555-9004)", 
                        Arrays.asList("Talent Acquisition", "Employee Onboarding", "Conflict Resolution", "Performance Mgmt")),

                createEmployee("EMP-HR-2002", "Michael", "Chang", "michael.chang@example.com", "+1 555-0165", 
                        "HR Operations Specialist", humanResources, EmploymentStatus.PROBATION, new BigDecimal("68000.00"), 
                        LocalDate.of(2025, 8, 1), LocalDate.of(1999, 4, 12), 
                        "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150&auto=format&fit=crop&q=80",
                        "452 Broadway, Boston, MA", "Alice Chang (+1 555-9005)", 
                        Arrays.asList("Workday", "HRIS", "Payroll", "Benefits Admin")),

                createEmployee("EMP-DES-3001", "Marcus", "Chen", "marcus.chen@example.com", "+1 555-0177", 
                        "Lead UI/UX Designer", productDesign, EmploymentStatus.ACTIVE, new BigDecimal("125000.00"), 
                        LocalDate.of(2023, 2, 10), LocalDate.of(1992, 1, 30), 
                        "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=150&auto=format&fit=crop&q=80",
                        "600 Oak Ridge Rd, Chicago, IL", "Jessica Chen (+1 555-9006)", 
                        Arrays.asList("Figma", "Design Systems", "User Research", "Prototyping", "Design Tokens")),

                createEmployee("EMP-DES-3002", "Chloe", "Dubois", "chloe.dubois@example.com", "+1 555-0129", 
                        "Product Designer", productDesign, EmploymentStatus.ON_LEAVE, new BigDecimal("92000.00"), 
                        LocalDate.of(2024, 3, 15), LocalDate.of(1997, 7, 22), 
                        "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=150&auto=format&fit=crop&q=80",
                        "15 Rue de Paris, Los Angeles, CA", "Pierre Dubois (+1 555-9007)", 
                        Arrays.asList("Figma", "Interaction Design", "Wireframing", "Animation")),

                createEmployee("EMP-MKT-4001", "Sophia", "Martinez", "sophia.martinez@example.com", "+1 555-0111", 
                        "Director of Growth Marketing", marketing, EmploymentStatus.ACTIVE, new BigDecimal("130000.00"), 
                        LocalDate.of(2022, 11, 1), LocalDate.of(1990, 10, 5), 
                        "https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?w=150&auto=format&fit=crop&q=80",
                        "880 Ocean View, Miami, FL", "Carlos Martinez (+1 555-9008)", 
                        Arrays.asList("Growth Marketing", "SEO/SEM", "Content Strategy", "Google Analytics", "Brand Campaigns")),

                createEmployee("EMP-FIN-5001", "David", "Sterling", "david.sterling@example.com", "+1 555-0155", 
                        "Financial Controller", finance, EmploymentStatus.ACTIVE, new BigDecimal("140000.00"), 
                        LocalDate.of(2021, 9, 1), LocalDate.of(1987, 12, 18), 
                        "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&auto=format&fit=crop&q=80",
                        "10 Wall St, New York, NY", "Victoria Sterling (+1 555-9009)", 
                        Arrays.asList("Financial Modeling", "Budgeting", "Audit Compliance", "QuickBooks", "SAP"))
        );

        employeeRepository.saveAll(employees);
    }

    private Employee createEmployee(String code, String firstName, String lastName, String email, String phone,
                                    String designation, Department department, EmploymentStatus status, BigDecimal salary,
                                    LocalDate joiningDate, LocalDate dob, String avatarUrl, String address, 
                                    String emergencyContact, List<String> skills) {
        Employee emp = new Employee();
        emp.setEmployeeCode(code);
        emp.setFirstName(firstName);
        emp.setLastName(lastName);
        emp.setEmail(email);
        emp.setPhone(phone);
        emp.setDesignation(designation);
        emp.setDepartment(department);
        emp.setStatus(status);
        emp.setSalary(salary);
        emp.setJoiningDate(joiningDate);
        emp.setDateOfBirth(dob);
        emp.setAvatarUrl(avatarUrl);
        emp.setAddress(address);
        emp.setEmergencyContact(emergencyContact);
        emp.setSkills(skills);
        return emp;
    }
}
