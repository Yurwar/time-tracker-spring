package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.UpdateUserDTO;
import com.yurwar.trainingcourse.entity.Authority;
import com.yurwar.trainingcourse.entity.User;
import com.yurwar.trainingcourse.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Log4j2
@RequestMapping
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getListOfUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);

        return "redirect:/users";
    }

    @GetMapping("/users/update/{id}")
    public String getUserUpdatePage(@PathVariable("id") long id, Model model) {
        User user = userService.findUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("authorities", Authority.values());
        return "update-user";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @ModelAttribute("user") @Valid UpdateUserDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authorities", Authority.values());
            return "update-user";
        }

        log.info("Updated user info" + userDTO);
        userService.updateUser(id, userDTO);

        return "redirect:/users";
    }
}
