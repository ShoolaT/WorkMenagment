package com.example.project.config;
import com.example.project.model.Authority;
import com.example.project.repository.AuthorityRepository;
import com.example.project.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Authority> authorities = authorityService.getAllAuthorities();

        if (authorities.size() == 0) {

            Authority director = new Authority();
            director.setName("DIRECTOR");
            authorityRepository.save(director);

            Authority project_manager = new Authority();
            project_manager.setName("PROJECT_MANAGER");
            authorityRepository.save(project_manager);

            Authority employee = new Authority();
            employee.setName("EMPLOYEE");
            authorityRepository.save(employee);
 }
    }

}