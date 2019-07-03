package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.dto.UserDTO;
import com.yurwar.trainingcourse.exception.IncorrectPasswordException;
import com.yurwar.trainingcourse.exception.LoginNotUniqueException;
import com.yurwar.trainingcourse.exception.NoSuchUserException;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<User> findAllUsers() {
        return new ArrayList<>(repository.findAll());
    }

    public void saveUser(User user) {
        try {
            repository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.error(user.getEmail() + " - Login not unique");
            throw new LoginNotUniqueException(user.getEmail() + " - Login not unique", e);
        }
    }

    public User loginUser(UserDTO userDTO) {
        User user = repository.findByEmail(userDTO.getEmail());

        if(user == null) {
            log.warn(userDTO + " there is no such user record in database");
            throw new NoSuchUserException("No such user " + userDTO.getEmail());
        }

        if (!userDTO.getPassword().equals(user.getPassword())) {
            log.warn(userDTO + " incorrect password");
            throw new IncorrectPasswordException("Incorrect password");
        }

        log.info(userDTO + " user successfully logged in");
        return user;
    }


}
