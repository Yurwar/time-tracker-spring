package com.yurwar.trainingcourse.web;

import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping
    public List<User> getListOfPetPhotos() {
        return userService.findAll();
    }
}
