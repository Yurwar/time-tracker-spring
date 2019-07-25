package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.entity.Role;
import com.yurwar.trainingcourse.entity.User;
import com.yurwar.trainingcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public String getListOfUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User userToDelete = userService.findUserById(id);

        userService.deleteUser(userToDelete);
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/users/update/{id}")
    public String getUserUpdatePage(@PathVariable("id") long id, Model model) {
        User user = userService.findUserById(id);

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "update-user";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") long id,
                             @Valid RegistrationUserDTO userDTO,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            return "update-user";
        }
        User user = userService.findUserById(id);

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAuthorities(userDTO.getAuthorities());
        userService.updateUser(user);
        model.addAttribute("users", userService.findAllUsers());
        return "users";
    }
}
