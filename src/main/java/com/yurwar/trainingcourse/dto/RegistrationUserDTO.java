package com.yurwar.trainingcourse.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationUserDTO {
    //TODO i18n
    @NotBlank(message = "First name must be not blank")
    @Size(min = 2, message = "Minimal length of first name must be more than 2")
    private String firstName;

    @NotBlank(message = "Last name must be not blank")
    @Size(min = 2, message = "Minimal length of last name must be more than 2")
    private String lastName;

    @NotBlank(message = "Username must be not blank")
    @Size(min = 5, message = "Minimal length of first name must be more than 6")
    private String username;

    @NotBlank(message = "Password must be not blank")
    @Size(min = 2, message = "Minimal length of password must be more than 2")
    private String password;
}
