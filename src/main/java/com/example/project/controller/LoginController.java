package com.example.project.controller;
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