package com.example.project.service;

import com.example.project.model.Authority;
import com.example.project.model.Employee;
import com.example.project.model.Role;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> mayBeUser = userRepository.getByEmail(username);
//        if (mayBeUser.isEmpty()) {
//            return new org.springframework.security.core.userdetails.User(
//                    " ",
//                    " ",
//                    getAuthorities(Collections.singletonList(roleRepository.findByRole("ROLE_GUEST"))));
//        }
        Employee employee = employeeRepository.getByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(),
                employee.getPassword(),
                employee.isEnabled(),
                true,
                true,
                true,
                getAuthorities(employee.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Authority> collection = new ArrayList<>();

        for (Role role : roles) {
            privileges.add(role.getRole());
            collection.addAll(role.getAuthorities());
        }
        for (Authority item : collection) {
            privileges.add(item.getAuthority());
        }
        // ROLE_USER, FULL, READ_ONLY
        return privileges;
    }

    public void processOAuthPostLogin(String username) {
        var existUser = employeeRepository.getByEmail(username);

        if (existUser.isEmpty()) {
            var user = Employee.builder()
                    .email(username)
                    .password(encoder.encode("qwerty"))
                    .roles(new HashSet<>())
                    .enabled(Boolean.TRUE)
                    .build();
            user.addRole(roleRepository.findByRole("ROLE_USER"));
            employeeRepository.saveAndFlush(user);
        }

        UserDetails userDetails = loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
