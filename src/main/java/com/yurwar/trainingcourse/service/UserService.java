package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static List<User> usersList = new ArrayList<>();
    static {
        usersList.add(new User(1,
                "Vasya",
                "Pupkin",
                "vasya.pupkin@gmail.com",
                "123456"));
        usersList.add(new User(1,
                "Mykola",
                "Sidorov",
                "mykola.sidor@mail.ru",
                "789456q"));
    }



    public List<User> findAll() {
        return usersList;
    }


}
