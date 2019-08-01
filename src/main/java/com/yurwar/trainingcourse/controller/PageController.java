package com.yurwar.trainingcourse.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController implements ErrorController {
    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedPage() {
        return "/error/403";
    }

    @Override
    @RequestMapping("/error")
    public String getErrorPath() {
        return "/error/500";
    }
}
