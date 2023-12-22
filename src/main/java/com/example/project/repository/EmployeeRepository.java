package com.example.project.repository;

import com.example.project.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {
    Optional<Employee> findOneByEmailIgnoreCase(String email);
    List<Employee> findByCompanyId(Long companyId);
    List<Employee> findByProjects_Id(Long projectId);
}
