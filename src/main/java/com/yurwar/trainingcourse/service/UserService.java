package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.exception.LoginNotUniqueException;
import com.yurwar.trainingcourse.model.Activity;
import com.yurwar.trainingcourse.model.Role;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log4j2
@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }



    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public void saveUser(RegistrationUserDTO userDTO) {
        try {
            User user = User
                    .builder()
                    .firstName(userDTO.getFirstName())
                    .lastName(userDTO.getLastName())
                    .username(userDTO.getUsername())
                    .password(new BCryptPasswordEncoder().encode(userDTO.getPassword()))
                    .enabled(true)
                    //Temp usage of one hardcode role
                    .authorities(Collections.singleton(Role.USER))
                    .build();
            repository.save(user);
            log.info("New user " + user);
        } catch (DataIntegrityViolationException e) {
            log.error(userDTO.getUsername() + " - Login not unique");
            throw new LoginNotUniqueException(userDTO.getUsername() + " - Login not unique", e);
        }
    }

    @Transactional
    public void addActivity(Long userId, Activity activity) {
        User user = repository.getOne(userId);
        user.getActivities().add(activity);
        repository.save(user);
    }

    public void addActivity(Activity... activities) {

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }
}
