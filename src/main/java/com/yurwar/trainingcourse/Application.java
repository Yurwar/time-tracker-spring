package com.yurwar.trainingcourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping("/registration-form")
    public String showRegForm() {
        return "registration-form.html";
    }

    @RequestMapping("/index")
    public String showIndex() {
        return "index.html";
    }

    @RequestMapping("/users")
    public String showUsers() {
        return "users-list.html";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login.html";
    }
}
