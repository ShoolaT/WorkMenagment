package com.example.project.repository;


import com.example.project.model.Authority;
import com.example.project.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}