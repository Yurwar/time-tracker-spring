package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.dto.UserDTO;
import com.yurwar.trainingcourse.exception.LoginNotUniqueException;
import com.yurwar.trainingcourse.model.Role;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegForm() {
        return "registration.html";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void registerNewUser(@RequestBody RegistrationUserDTO registrationUserDTO) {
        log.info("{}", registrationUserDTO);
        userService.saveUser(registrationUserDTO);
    }

    @ExceptionHandler(LoginNotUniqueException.class)
    public ResponseEntity<LoginNotUniqueException> handleRuntimeException(LoginNotUniqueException e, Map<String, Object> model) {
        return ResponseEntity
                .badRequest()
                .body(e);
    }
}
