package com.yurwar.trainingcourse.controller;

import com.yurwar.trainingcourse.dto.UpdateUserDTO;
import com.yurwar.trainingcourse.model.entity.Authority;
import com.yurwar.trainingcourse.model.entity.User;
import com.yurwar.trainingcourse.model.service.UserService;
import com.yurwar.trainingcourse.util.exception.UsernameNotUniqueException;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller that react on user related requests
 *
 * @see com.yurwar.trainingcourse.model.entity.Activity
 */
@Controller
@Log4j2
@RequestMapping
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getListOfUsers(Model model,
                                 @PageableDefault(size = 15,
                                         sort = {"lastName", "firstName"}) Pageable pageable) {
        model.addAttribute("users", userService.findAllUsersPageable(pageable));
        return "users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/users/update/{id}")
    public String getUserUpdatePage(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);

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

        try {
            userService.updateUser(id, userDTO);
        } catch (UsernameNotUniqueException e) {
            model.addAttribute("usernameErrorMessage", e.getMessage());
            model.addAttribute("authorities", Authority.values());
            return "update-user";
        }

        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String getUserProfilePage(@AuthenticationPrincipal User user,
                                     Model model) {
        model.addAttribute("user", userService.getUserById(user.getId()));
        return "user-profile";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return "error/404";
    }
}
