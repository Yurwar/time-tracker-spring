package com.yurwar.trainingcourse.dto;


import lombok.*;

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
}
