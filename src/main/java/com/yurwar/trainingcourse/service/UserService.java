package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.exception.LoginNotUniqueException;
import com.yurwar.trainingcourse.model.Activity;
import com.yurwar.trainingcourse.model.Role;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
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
    private final ReloadableResourceBundleMessageSource messageSource;
    private final BCryptPasswordEncoder passwordEncorder;

    @Autowired
    public UserService(UserRepository repository,
                       ReloadableResourceBundleMessageSource messageSource,
                       BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.messageSource = messageSource;
        this.passwordEncorder = passwordEncoder;
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
                    .password(passwordEncorder.encode(userDTO.getPassword()))
                    .enabled(true)
                    //Temp usage of one hardcode role
                    .authorities(Collections.singleton(Role.USER))
                    .build();
            repository.save(user);
            log.info("New user " + user);
        } catch (DataIntegrityViolationException e) {
            log.error("Login not unique: " + userDTO.getUsername());
            throw new LoginNotUniqueException(messageSource.getMessage(
                    "users.registration.login.not_unique",
                    null,
                    LocaleContextHolder.getLocale()) + userDTO.getUsername(), e);
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
