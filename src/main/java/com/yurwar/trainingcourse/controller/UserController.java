package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public List<User> getListOfUsers() {
        return userService.findAllUsers();
    }
}
