package com.yurwar.trainingcourse.dto;

import com.yurwar.trainingcourse.entity.Authority;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UpdateUserDTO {
    //TODO i18n
    private long id;

    @NotBlank(message = "First name must not be blank")
    @Size(min = 2, max = 255, message = "First name size must be from 2 to 255")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    @Size(min = 2, max = 255, message = "Last name size must be from 2 to 255")
    private String lastName;

    @NotBlank(message = "Username must not be blank")
    @Size(min = 5, max = 255, message = "Username size must be from 5 to 255")
    private String username;

    private String password;

    @Size(min = 1, max = 2, message = "Must be at least one authority")
    private Set<Authority> authorities;
}
