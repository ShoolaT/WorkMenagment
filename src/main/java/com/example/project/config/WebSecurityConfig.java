package com.example.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                //.headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/webjars/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/rss/**").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/home").permitAll()
//                        .requestMatchers("/login").anonymous()
                        .anyRequest().fullyAuthenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            // Redirect only authenticated users on logout
//                            if (authentication != null && authentication.isAuthenticated()) {
//                                response.sendRedirect("/login?logout");
//                            }
//                        })
                )
//                .exceptionHandling(exceptions -> exceptions
//                        .authenticationEntryPoint((request, response, authException) -> {
//                            // Redirect authenticated users trying to access login page
//                            if (authException != null && authException instanceof Authentication) {
//                                Authentication authentication = (Authentication) authException;
//                                if (authentication.isAuthenticated()) {
//                                    response.sendRedirect("/"); // Redirect to home or any other page
//                                }
//                            }
//                        })
//                )
               ;

        return http.build();
    }

}