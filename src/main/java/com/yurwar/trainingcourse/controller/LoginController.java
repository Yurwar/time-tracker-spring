package com.yurwar.trainingcourse.controller;


import com.yurwar.trainingcourse.dto.UserDTO;
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
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(value = "/login-user", method = RequestMethod.POST)
    public void loginUser(UserDTO userDTO) {
        log.info("User login data: " + userDTO);
        if(userService.isUserExist(userDTO)) {
            log.info("User successfully logged in");
        } else {
            log.info("User doesn't logged in");
        }
    }
}
