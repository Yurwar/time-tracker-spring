package com.yurwar.trainingcourse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedPage() {
        return "/error/access-denied";
    }
}
