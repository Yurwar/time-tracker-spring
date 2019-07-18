package com.yurwar.trainingcourse.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginUserDTO {
    private String email;
    private String password;
}
