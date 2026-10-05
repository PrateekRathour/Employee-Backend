package com.emp.management.repository;

import com.emp.management.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByNameIgnoreCase(String name);

    Optional<Department> findByCodeIgnoreCase(String code);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByCodeIgnoreCase(String code);

    @Query("SELECT d FROM Department d WHERE " +
           "(:query IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(d.code) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:location IS NULL OR LOWER(d.location) LIKE LOWER(CONCAT('%', :location, '%')))")
    List<Department> searchDepartments(@Param("query") String query, @Param("location") String location);
}
