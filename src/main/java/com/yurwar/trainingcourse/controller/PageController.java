package com.yurwar.trainingcourse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/index")
    public String getIndexPage() {
        return "index.html";
    }

    @GetMapping("/users")
    public String getUsersPage() {
        return "users.html";
    }
}
