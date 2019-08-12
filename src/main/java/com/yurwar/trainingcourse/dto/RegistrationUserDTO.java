package com.yurwar.trainingcourse.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Data transfer object to transport user data from registration controller to service
 *
 * @author Yurii Matora
 * @see com.yurwar.trainingcourse.model.entity.User
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationUserDTO {
    @NotBlank(message = "{validation.user.first_name.not_blank}")
    @Size(min = 2, max = 50, message = "{validation.user.first_name.size}")
    private String firstName;

    @NotBlank(message = "{validation.user.last_name.not_blank}")
    @Size(min = 2, max = 50, message = "{validation.user.last_name.size}")
    private String lastName;

    @NotBlank(message = "{validation.user.username.not_blank}")
    @Size(min = 5, max = 39, message = "{validation.user.username.size}")
    private String username;

    @NotBlank(message = "{validation.user.password.not_blank}")
    @Size(min = 5, max = 39, message = "{validation.user.password.size}")
    private String password;
}
