-- ==========================================================
-- Employee Management System - MySQL Database Schema
-- Compatible with MySQL 8.0+, MariaDB, & MySQL Workbench
-- ==========================================================

CREATE DATABASE IF NOT EXISTS `employee_management_db`
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE `employee_management_db`;

-- Drop existing tables in reverse dependency order
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `employee_skills`;
DROP TABLE IF EXISTS `employees`;
DROP TABLE IF EXISTS `departments`;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. Departments Table
CREATE TABLE `departments` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL UNIQUE,
    `code` VARCHAR(20) NOT NULL UNIQUE,
    `description` VARCHAR(500) NULL,
    `location` VARCHAR(100) NULL,
    `head_of_department` VARCHAR(100) NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    INDEX `idx_dept_name` (`name`),
    INDEX `idx_dept_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Employees Table
CREATE TABLE `employees` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `employee_code` VARCHAR(30) NOT NULL UNIQUE,
    `first_name` VARCHAR(60) NOT NULL,
    `last_name` VARCHAR(60) NOT NULL,
    `email` VARCHAR(120) NOT NULL UNIQUE,
    `phone` VARCHAR(25) NULL,
    `designation` VARCHAR(100) NOT NULL,
    `department_id` BIGINT NOT NULL,
    `status` ENUM('ACTIVE', 'ONBOARDING', 'PROBATION', 'ON_LEAVE', 'TERMINATED') NOT NULL DEFAULT 'ONBOARDING',
    `salary` DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    `joining_date` DATE NOT NULL,
    `date_of_birth` DATE NULL,
    `avatar_url` VARCHAR(500) NULL,
    `address` VARCHAR(255) NULL,
    `emergency_contact` VARCHAR(100) NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT `fk_employee_department`
        FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX `idx_emp_email` (`email`),
    INDEX `idx_emp_code` (`employee_code`),
    INDEX `idx_emp_status` (`status`),
    INDEX `idx_emp_dept` (`department_id`),
    INDEX `idx_emp_name` (`first_name`, `last_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Employee Skills Association Table
CREATE TABLE `employee_skills` (
    `employee_id` BIGINT NOT NULL,
    `skill_name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`employee_id`, `skill_name`),
    CONSTRAINT `fk_skills_employee`
        FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX `idx_skill_name` (`skill_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
