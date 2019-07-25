package com.yurwar.trainingcourse.dto;


import com.yurwar.trainingcourse.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationUserDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Set<Role> authorities;
}
