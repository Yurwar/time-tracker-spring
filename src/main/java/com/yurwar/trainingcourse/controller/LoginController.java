package com.yurwar.trainingcourse.controller;


import com.yurwar.trainingcourse.dto.LoginUserDTO;
import com.yurwar.trainingcourse.exception.LoginException;
import com.yurwar.trainingcourse.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Controller
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

//    @ResponseStatus(HttpStatus.ACCEPTED)
//    @PostMapping
//    public void loginUser(LoginUserDTO loginUserDTO) {
//        userService.loginUser(loginUserDTO);
//    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Exception> handleLoginException(LoginException e) {
        log.warn(e.getMessage());
        return ResponseEntity
                .badRequest()
                .body(e);
    }
}
