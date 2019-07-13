package com.yurwar.trainingcourse.service;

import com.yurwar.trainingcourse.dto.RegistrationUserDTO;
import com.yurwar.trainingcourse.exception.LoginNotUniqueException;
import com.yurwar.trainingcourse.model.Activity;
import com.yurwar.trainingcourse.model.ActivityRequest;
import com.yurwar.trainingcourse.model.Role;
import com.yurwar.trainingcourse.model.User;
import com.yurwar.trainingcourse.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncorder;

    @Autowired
    public UserService(UserRepository userRepository,
                       MessageSource messageSource,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.passwordEncorder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
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
            userRepository.save(user);
            log.info("New user " + user);
        } catch (DataIntegrityViolationException e) {
            log.error("Login not unique: " + userDTO.getUsername());
            throw new LoginNotUniqueException(messageSource.getMessage(
                    "users.registration.login.not_unique",
                    null,
                    LocaleContextHolder.getLocale()) + userDTO.getUsername(), e);
        }
    }

    public void deleteUser(User userToDelete) {
        userRepository.delete(userToDelete);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void addActivityRequest(Long userId, Activity activity) {
        User user = userRepository.getOne(userId);
        user.getActivityRequests().add(ActivityRequest.builder()
                .user(user)
                .activity(activity)
                .build());
        userRepository.save(user);
    }

    public void addActivityRequest(Long userId, Activity... activities) {
        User user = userRepository.getOne(userId);
        Arrays.stream(activities).forEach(activity -> user
                .getActivityRequests()
                .add(ActivityRequest.builder()
                        .user(user)
                        .activity(activity)
                        .build()));
        userRepository.save(user);
    }
}
