package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<User> findAllUsers() {
        List<User> allUsersList = new ArrayList<>();
        repository.findAll().forEach(allUsersList::add);
        return allUsersList;
    }

    public void saveUser(User user) {
        repository.save(user);
    }


}
