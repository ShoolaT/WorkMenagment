package com.example.project.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


   // @Secured("isAnonymous()")
    @GetMapping("/login")
    public String getLogin() {

        return "login";
    }
}