package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.service.UserService;
import com.yurwar.trainingcourse.util.exception.LoginNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping
    public String registerNewUser(Model model, @Valid RegistrationUserDTO registrationUserDTO) {
        log.info("{}", registrationUserDTO);
        userService.saveUser(registrationUserDTO);
        model.addAttribute("resultMessage",
                messageSource.getMessage("users.registration.success",
                        null,
                        LocaleContextHolder.getLocale()));
        return "redirect:/login";
    }

    @ExceptionHandler(LoginNotUniqueException.class)
    public String handleRuntimeException(Model model, LoginNotUniqueException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "registration";
    }
}
