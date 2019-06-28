package com.yurwar.trainingcourse.model;


import lombok.Data;

@Data
public class User {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
}
