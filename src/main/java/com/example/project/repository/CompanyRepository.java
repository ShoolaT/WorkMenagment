package com.example.project.repository;

import com.example.project.model.Company;
import com.example.project.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>, PagingAndSortingRepository<Company, Long> {

}
