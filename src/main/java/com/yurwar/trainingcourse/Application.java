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

    @RequestMapping("/hello")
    public String showHelloWorld() {
        return "hello-world";
    }

    @RequestMapping("/")
    public String showIndex() {
        return "index.html";
    }
}
