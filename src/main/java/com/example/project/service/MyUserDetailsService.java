package com.example.project.service;
import com.example.project.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee = employeeService.findOneByEmail(email);
        if (!optionalEmployee.isPresent()) {
            throw new UsernameNotFoundException("Employee not found");
        }
        Employee employee = optionalEmployee.get();
        List<GrantedAuthority> grantedAuthorities = employee
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(employee.getEmail(), employee.getPassword(), grantedAuthorities); // (2)
    }


}