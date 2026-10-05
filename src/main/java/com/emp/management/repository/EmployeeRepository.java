package com.emp.management.repository;

import com.emp.management.entity.Employee;
import com.emp.management.entity.EmploymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmailIgnoreCase(String email);

    Optional<Employee> findByEmployeeCodeIgnoreCase(String employeeCode);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmployeeCodeIgnoreCase(String employeeCode);

    long countByStatus(EmploymentStatus status);

    long countByDepartmentId(Long departmentId);

    List<Employee> findByDepartmentId(Long departmentId);

    @Query("SELECT e FROM Employee e WHERE " +
           "(:query IS NULL OR :query = '' OR " +
           " LOWER(e.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(e.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(e.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(e.designation) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:departmentId IS NULL OR e.department.id = :departmentId) AND " +
           "(:status IS NULL OR e.status = :status) AND " +
           "(:designation IS NULL OR :designation = '' OR LOWER(e.designation) LIKE LOWER(CONCAT('%', :designation, '%')))")
    Page<Employee> filterEmployees(
            @Param("query") String query,
            @Param("departmentId") Long departmentId,
            @Param("status") EmploymentStatus status,
            @Param("designation") String designation,
            Pageable pageable);

    @Query("SELECT e FROM Employee e ORDER BY e.joiningDate DESC, e.createdAt DESC")
    List<Employee> findRecentJoiners(Pageable pageable);
}
