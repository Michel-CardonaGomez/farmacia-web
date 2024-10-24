package com.farmacia_web.farmacia_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {

        return "inicio";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}