package com.yurwar.trainingcourse.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
