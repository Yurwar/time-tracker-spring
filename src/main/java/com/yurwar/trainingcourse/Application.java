package com.yurwar.trainingcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/registration-form")
    public String showRegForm() {
        return "registration-form.html";
    }

    @GetMapping("/index")
    public String showIndex() {
        return "index.html";
    }

    @GetMapping("/users")
    public String showUsers() {
        return "users-list.html";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login.html";
    }
}
