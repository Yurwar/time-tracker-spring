package com.yurwar.trainingcourse.controller;


import com.yurwar.trainingcourse.dto.UserDTO;
import com.yurwar.trainingcourse.exception.LoginException;
import com.yurwar.trainingcourse.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@Controller
@RequestMapping(value = "/login-user")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST)
    public void loginUser(UserDTO userDTO) {
        userService.loginUser(userDTO);
    }

    @ExceptionHandler({LoginException.class})
    public ResponseEntity<String> handleLoginException(LoginException e) {
        log.warn(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("{\"message\": \"" + e.getMessage() + "\"}");
    }
}
