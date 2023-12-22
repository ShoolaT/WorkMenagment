package com.example.project.repository;


import com.example.project.model.Authority;
import com.example.project.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    List<Authority> findByNameIn(List<String> names);

}