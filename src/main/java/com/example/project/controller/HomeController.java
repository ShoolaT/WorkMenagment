package com.example.project.controller;
//import com.example.blog.entity.Post;
//import com.example.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
//    @Autowired
//    private PostService postService;

    @GetMapping("/")
    public String home(Model model) {
//        List<Post> posts = postService.getAll();

//        Post randPost = postService.getRand();
//        model.addAttribute("posts", posts);
//        model.addAttribute("randPost", randPost);
        return "home";
    }









}