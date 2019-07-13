package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.exception.LoginNotUniqueException;
import com.yurwar.trainingcourse.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public RegistrationController(UserService userService,
                                  MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String getRegistrationPage() {
        return "registration";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String registerNewUser(Model model, @Valid RegistrationUserDTO registrationUserDTO) {
        log.info("{}", registrationUserDTO);
        userService.saveUser(registrationUserDTO);
        model.addAttribute("resultMessage",
                messageSource.getMessage("users.registration.success",
                        null,
                        LocaleContextHolder.getLocale()));
        return "registration";
    }

    @ExceptionHandler(LoginNotUniqueException.class)
    public String handleRuntimeException(Model model, LoginNotUniqueException e) {
        model.addAttribute("resultMessage", e.getMessage());
        return "registration";
    }
}
