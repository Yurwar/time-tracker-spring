package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.UserDTO;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@Controller
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerNewUser(UserDTO userDTO) {
        log.info("{}", userDTO);
        userService.saveUser(new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getPassword()));
        return "registration-form";
    }
}
