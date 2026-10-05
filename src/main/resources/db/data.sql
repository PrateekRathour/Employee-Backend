-- ==========================================================
-- Employee Management System - Initial Seed Data Script
-- ==========================================================

USE `employee_management_db`;

-- 1. Insert Departments
INSERT INTO `departments` (`id`, `name`, `code`, `description`, `location`, `head_of_department`, `created_at`, `updated_at`) VALUES
(1, 'Engineering & Technology', 'ENG', 'Core software engineering, cloud architecture, devops, and infrastructure', 'Building A - Floor 4', 'Dr. Alexander Wright', NOW(), NOW()),
(2, 'Human Resources', 'HR', 'Talent acquisition, employee lifecycle, workplace culture, and compliance', 'Building B - Floor 2', 'Elena Vance', NOW(), NOW()),
(3, 'Product & UI/UX Design', 'DES', 'User research, interface design, design systems, and product strategy', 'Building A - Floor 3', 'Marcus Chen', NOW(), NOW()),
(4, 'Global Marketing & Growth', 'MKT', 'Brand positioning, digital marketing, demand generation, and PR', 'Building C - Floor 1', 'Sophia Martinez', NOW(), NOW()),
(5, 'Finance & Operations', 'FIN', 'Corporate finance, auditing, payroll management, and forecasting', 'Building B - Floor 5', 'David Sterling', NOW(), NOW());

-- 2. Insert Employees
INSERT INTO `employees` (`id`, `employee_code`, `first_name`, `last_name`, `email`, `phone`, `designation`, `department_id`, `status`, `salary`, `joining_date`, `date_of_birth`, `avatar_url`, `address`, `emergency_contact`, `created_at`, `updated_at`) VALUES
(1, 'EMP-ENG-1001', 'Prateek', 'Sharma', 'prateek.sharma@example.com', '+1 555-0192', 'Lead DevOps & Cloud Architect', 1, 'ACTIVE', 135000.00, '2023-01-15', '1994-06-20', 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&auto=format&fit=crop&q=80', '742 Evergreen Terrace, Seattle, WA', 'Dr. Alexander Wright (+1 555-9001)', NOW(), NOW()),
(2, 'EMP-ENG-1002', 'Sarah', 'Jenkins', 'sarah.jenkins@example.com', '+1 555-0144', 'Senior Full Stack Engineer', 1, 'ACTIVE', 120000.00, '2023-04-01', '1996-03-14', 'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&auto=format&fit=crop&q=80', '1204 Pine St, San Francisco, CA', 'Mark Jenkins (+1 555-9002)', NOW(), NOW()),
(3, 'EMP-ENG-1003', 'Aarav', 'Patel', 'aarav.patel@example.com', '+1 555-0188', 'Cloud Platform Engineer', 1, 'ONBOARDING', 98000.00, '2025-09-10', '1998-11-28', 'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&auto=format&fit=crop&q=80', '89 Silicon Ave, Austin, TX', 'Priya Patel (+1 555-9003)', NOW(), NOW()),
(4, 'EMP-HR-2001', 'Elena', 'Vance', 'elena.vance@example.com', '+1 555-0133', 'VP of People & Culture', 2, 'ACTIVE', 145000.00, '2022-05-20', '1988-08-09', 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=150&auto=format&fit=crop&q=80', '330 Park Avenue, New York, NY', 'Thomas Vance (+1 555-9004)', NOW(), NOW()),
(5, 'EMP-HR-2002', 'Michael', 'Chang', 'michael.chang@example.com', '+1 555-0165', 'HR Operations Specialist', 2, 'PROBATION', 68000.00, '2025-08-01', '1999-04-12', 'https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150&auto=format&fit=crop&q=80', '452 Broadway, Boston, MA', 'Alice Chang (+1 555-9005)', NOW(), NOW()),
(6, 'EMP-DES-3001', 'Marcus', 'Chen', 'marcus.chen@example.com', '+1 555-0177', 'Lead UI/UX Designer', 3, 'ACTIVE', 125000.00, '2023-02-10', '1992-01-30', 'https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=150&auto=format&fit=crop&q=80', '600 Oak Ridge Rd, Chicago, IL', 'Jessica Chen (+1 555-9006)', NOW(), NOW()),
(7, 'EMP-DES-3002', 'Chloe', 'Dubois', 'chloe.dubois@example.com', '+1 555-0129', 'Product Designer', 3, 'ON_LEAVE', 92000.00, '2024-03-15', '1997-07-22', 'https://images.unsplash.com/photo-1580489944761-15a19d654956?w=150&auto=format&fit=crop&q=80', '15 Rue de Paris, Los Angeles, CA', 'Pierre Dubois (+1 555-9007)', NOW(), NOW()),
(8, 'EMP-MKT-4001', 'Sophia', 'Martinez', 'sophia.martinez@example.com', '+1 555-0111', 'Director of Growth Marketing', 4, 'ACTIVE', 130000.00, '2022-11-01', '1990-10-05', 'https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?w=150&auto=format&fit=crop&q=80', '880 Ocean View, Miami, FL', 'Carlos Martinez (+1 555-9008)', NOW(), NOW()),
(9, 'EMP-FIN-5001', 'David', 'Sterling', 'david.sterling@example.com', '+1 555-0155', 'Financial Controller', 5, 'ACTIVE', 140000.00, '2021-09-01', '1987-12-18', 'https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&auto=format&fit=crop&q=80', '10 Wall St, New York, NY', 'Victoria Sterling (+1 555-9009)', NOW(), NOW());

-- 3. Insert Employee Skills
INSERT INTO `employee_skills` (`employee_id`, `skill_name`) VALUES
(1, 'Kubernetes'), (1, 'AWS'), (1, 'Docker'), (1, 'Terraform'), (1, 'CI/CD'), (1, 'Java'), (1, 'Spring Boot'),
(2, 'Angular'), (2, 'TypeScript'), (2, 'Spring Boot'), (2, 'MySQL'), (2, 'RxJS'), (2, 'Material UI'),
(3, 'AWS'), (3, 'Jenkins'), (3, 'Linux'), (3, 'Docker'), (3, 'Python'), (3, 'Prometheus'),
(4, 'Talent Acquisition'), (4, 'Employee Onboarding'), (4, 'Conflict Resolution'), (4, 'Performance Mgmt'),
(5, 'Workday'), (5, 'HRIS'), (5, 'Payroll'), (5, 'Benefits Admin'),
(6, 'Figma'), (6, 'Design Systems'), (6, 'User Research'), (6, 'Prototyping'), (6, 'Design Tokens'),
(7, 'Figma'), (7, 'Interaction Design'), (7, 'Wireframing'), (7, 'Animation'),
(8, 'Growth Marketing'), (8, 'SEO/SEM'), (8, 'Content Strategy'), (8, 'Google Analytics'), (8, 'Brand Campaigns'),
(9, 'Financial Modeling'), (9, 'Budgeting'), (9, 'Audit Compliance'), (9, 'QuickBooks'), (9, 'SAP');
