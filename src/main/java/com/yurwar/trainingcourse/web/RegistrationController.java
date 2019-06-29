package com.yurwar.trainingcourse.web;

import com.yurwar.trainingcourse.dto.UserDTO;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void registerNewUser(UserDTO user) {
        System.out.println(user.getFirstName() + " " + user.getEmail());
        userService.saveUser(new User(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword())
        );
    }
}
